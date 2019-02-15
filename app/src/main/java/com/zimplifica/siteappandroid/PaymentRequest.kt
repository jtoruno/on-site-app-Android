package com.zimplifica.siteappandroid

import android.Manifest
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.media.RingtoneManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.*
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.pchmn.materialchips.ChipsInput
import com.pchmn.materialchips.model.Chip
import com.pchmn.materialchips.model.ChipInterface
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager
import com.zimplifica.siteappandroid.Models.Bill
import com.zimplifica.siteappandroid.Models.Card
import com.zimplifica.siteappandroid.utils.*
import java.text.SimpleDateFormat
import java.util.*

class PaymentRequest : AppCompatActivity() {
    //lateinit var noContactsTxt : TextView
    lateinit var btnToPay : Button
    //lateinit var recyclerView: RecyclerView
    private val CONTACTS_REQUEST = 100
    private val REQUEST_CODE = 300
    private var cardList  = mutableListOf<Card>()
    lateinit var listView : ListView
    //lateinit var splitRecyclerView : RecyclerView
    lateinit var splitAdapter : SplitFareAdapter
    //lateinit var searchView : SearchView
    lateinit var contactAdapter : ContactsAdapter
    lateinit var qrImg : ImageView
    //lateinit var userTxt : EditText
    lateinit var description : EditText
    //lateinit var splitTxt : TextView
    lateinit var paymentRow : TableRow


    lateinit var chipsInput : ChipsInput

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_request)
        val toolbar : Toolbar = findViewById(R.id.payment_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        //recyclerView = findViewById(R.id.recycler_contact_list)
        btnToPay = findViewById(R.id.button_to_pay_request)


        chipsInput = findViewById(R.id.chips_input)


        //searchView = findViewById(R.id.contacts_search_view)
        description = findViewById(R.id.editText2)
        listView = findViewById(R.id.list_view_payment)
        paymentRow = findViewById(R.id.payment_method_row)
        //userTxt = findViewById(R.id.user_text_view)
        //splitTxt = findViewById(R.id.split_pay_txt)
        //splitRecyclerView = findViewById(R.id.recycler_split_contact)
        //noContactsTxt = findViewById(R.id.textView4)
        //noContactsTxt.visibility = View.GONE
        val title = this.intent.getStringExtra("amount")
        toolbar.title =  "₡" + String.format("%,.2f", title.toDouble())
        val requestFlag = this.intent.getBooleanExtra("request", false)
        val chipList = mutableListOf<Chip>()
        if(requestFlag){
            paymentRow.visibility = View.GONE
            chipList.add(Chip("Walter Centeno","Usuario"))
            chipList.add(Chip("Alonso Solís","Usuario"))
            chipList.add(Chip("Rolando Fonseca","Usuario"))
            chipList.add(Chip("Joel Campbell","Usuario"))
            chipList.add(Chip("Hernán Medford","Usuario"))
            chipList.add(Chip("Keylor Navas","Usuario"))
        }else{
            chipList.add(Chip(R.drawable.mc_logo,"McDonald's", "Restaurante"))
            chipList.add(Chip("Na Praia", "Restaurante"))
            chipList.add(Chip(R.drawable.ic_access_time_black_24dp,"Chancay", "Restaurante"))
            chipList.add(Chip(R.drawable.dennys_logo,"Denny's", "Restaurante"))
            chipList.add(Chip(R.drawable.dennys_logo,"Cinemark", "Cine"))
        }
        chipsInput.filterableList = chipList
        val layoutManager = LinearLayoutManager(this)
        /*
        recyclerView.layoutManager = layoutManager
        val dividerItem = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItem)*/
        //requestPermission()
        cardList.add(Card("visa","9492"))
        cardList.add(Card("mastercard","6416"))
        listView.adapter = CardAdapter(this, R.layout.card_row, cardList.subList(0,1))
        listView.setOnItemClickListener { _, _, _, _ ->
            recyclerDialog()
        }
        /*
        val contactList = mutableListOf<ContactModel>()
        splitAdapter = SplitFareAdapter(this,contactList)
        val flowManager = FlowLayoutManager()
        flowManager.isAutoMeasureEnabled = true
        splitRecyclerView.layoutManager = flowManager
        //recyclerView.setHasFixedSize(true)
        splitRecyclerView.adapter = splitAdapter
        */
        /*
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                contactAdapter.filter.filter(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                contactAdapter.filter.filter(p0)
                return false
            }

        })*/

        qrImg = findViewById(R.id.imageView4)
        qrImg.setOnClickListener {
            startActivityForResult(Intent(this,QR::class.java), REQUEST_CODE)
        }
        btnToPay.setOnClickListener {
            if(chipsInput.selectedChipList.isEmpty()){
                val view = findViewById<View>(android.R.id.content)
                val snackBar = Snackbar.make(view,"Ingrese un usuario.", Snackbar.LENGTH_LONG)
                snackBar.view.setOnClickListener {
                    snackBar.dismiss()
                }
                snackBar.show()
            }
            else{
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Confirmación")
                dialog.setMessage("¿Desea realizar la transacción?")
                dialog.setPositiveButton("Confirmar"){
                        _, _ ->
                    val chip = chipsInput.selectedChipList[0]
                    val date = Date()
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy").format(date)
                    val hourFormat = SimpleDateFormat("HH:mm").format(date)
                    val model = Bill(UUID.randomUUID(),chip.label,
                        description.text.toString(),"₡" + String.format("%,.2f", title.toDouble()),"Francisco Córdoba Rojas",
                        dateFormat, hourFormat, chip.info , requestFlag)

                    val returnIntent = Intent()
                    returnIntent.putExtra("bill",model )
                    setResult(Activity.RESULT_OK, returnIntent)
                    if (requestFlag){
                        val intent = Intent(this,RequestBill::class.java)
                        intent.putExtra("bill", model)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        sendNotification("Pago realizado con éxito", "Su pago a ${model.enterprise} fue realizado de manera correcta. ")
                        val intent = Intent(this, PaymentBill::class.java)
                        intent.putExtra("bill", model)
                        startActivity(intent)
                        finish()
                    }
                }
                dialog.setNeutralButton("Cancelar"){_, _-> }
                dialog.show()
            }
        }
        /*
        splitTxt.paintFlags = splitTxt.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        splitTxt.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK)
            i.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(i, CONTACTS_REQUEST)
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("PaymentResult", requestCode.toString() + resultCode.toString())
        if(requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                val result = data?.getStringExtra("qr")?: ""
                chipsInput.addChip(result, "Otro")
                //userTxt.setText(result,  TextView.BufferType.EDITABLE)
            }
        }
        /*
        if(requestCode == CONTACTS_REQUEST && resultCode == Activity.RESULT_OK){
            val uri = data?.data
            val cursor = contentResolver.query(uri, null, null, null, null)
            if(cursor!=null && cursor.moveToFirst()){
                val phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val phone = cursor.getString(phoneIndex)
                val name = cursor.getString(nameIndex)
                val contact = ContactModel(name,phone, true)
                splitAdapter.addContact(contact)
                Log.e("REsult",phone + name)
            }
            cursor?.close()
        }*/
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        finish()
    }

    fun recyclerDialog(){
        val alertDialog = AlertDialog.Builder(this,R.style.CustomDialogTheme)
        val row = layoutInflater.inflate(R.layout.row_card_recycler, null)
        val recyclerView = row.findViewById<RecyclerView>(R.id.recyclerCardRow)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = RecyclerCard(this,cardList)
        alertDialog.setView(row)
        alertDialog.setPositiveButton("Cerrar"){
                dialog, _ ->
            dialog.cancel()
        }
        alertDialog.setTitle("Seleccionar Método de Pago")
        val alert = alertDialog.create()
        val window = alert.window
        val wlp = window?.attributes
        wlp?.gravity = Gravity.BOTTOM
        window?.attributes = wlp
        recyclerView.addOnItemClickListener(object: OnItemClickListener{
            override fun onItemClicked(position: Int, view: View) {
                val list = mutableListOf<Card>()
                list.add(cardList[position])
                listView.adapter = CardAdapter(this@PaymentRequest,R.layout.card_row, list)
                alert.cancel()
            }
        })
        alert.show()
    }

    fun sendNotification(message : String, title : String){
        val defaultSong = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelId = getString(R.string.default_notification_channel_id)
        val notification = NotificationCompat.Builder(this, channelId)
            .setSound(defaultSong)
            .setSmallIcon(R.drawable.chancay_logo)
            .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message))
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        manager.notify(/*notification id*/0,notification.build())
    }


    /*

    private fun getContacts():MutableList<ContactModel>  {
        val list = mutableListOf<ContactModel>()
        val resolver: ContentResolver = contentResolver
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
            ContactsContract.Contacts.DISPLAY_NAME+" ASC")

        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = (cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                if (phoneNumber > 0) {
                    val cursorPhone = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                    if(cursorPhone!!.count > 0) {
                        while (cursorPhone.moveToNext()) {
                            val phoneNumValue = cursorPhone.getString(
                                cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            list.add(ContactModel(name,phoneNumValue, false))
                        }
                    }
                    cursorPhone.close()
                }
            }
        } else {
            //   toast("No contacts available!")
        }
        cursor.close()
        return list
    }

    fun requestPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    PERMISSIONS_REQUEST_READ_CONTACTS)
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else{
            val list = getContacts()
            if(list.isEmpty()){
                noContactsTxt.visibility = View.VISIBLE
            }
            contactAdapter = ContactsAdapter(this,list){contactModel, b ->
                if (b){
                    splitAdapter.deleteContact(contactModel)
                }else{
                    splitAdapter.addContact(contactModel)
                }
            }

            recyclerView.adapter = contactAdapter
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val list = getContacts()
                    if(list.isEmpty()){
                        noContactsTxt.visibility = View.VISIBLE
                    }
                    contactAdapter = ContactsAdapter(this,list){contactModel, b ->
                        if (b){
                            splitAdapter.deleteContact(contactModel)
                        }else{
                            splitAdapter.addContact(contactModel)
                        }
                    }
                    recyclerView.adapter = contactAdapter

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.



                } else {
                    Toast.makeText(this,"No se dispone de permisos",Toast.LENGTH_LONG).show()
                    noContactsTxt.visibility = View.GONE
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }*/
}

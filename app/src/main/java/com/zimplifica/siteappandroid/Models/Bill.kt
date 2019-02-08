package com.zimplifica.siteappandroid.Models

import java.io.Serializable
import java.util.*

class Bill(val uuid: UUID, val enterprise : String, val description : String, val amount : String, val user : String, val date : String, val hour : String) : Serializable {
}
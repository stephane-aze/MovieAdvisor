package com.masterprojet.films.ui

import android.text.Editable

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
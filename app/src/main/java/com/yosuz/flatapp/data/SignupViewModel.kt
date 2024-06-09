package com.yosuz.flatapp.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yosuz.flatapp.based.User
import com.yosuz.flatapp.data.rules.Validator
import com.yosuz.flatapp.navigation.FlatAppRouter
import com.yosuz.flatapp.navigation.Screen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SignupViewModel : ViewModel() {

    private val TAG = SignupViewModel::class.simpleName

    var registrationUIState = mutableStateOf(RegistrationUIState())

    var allValidationsPassed = mutableStateOf(false)

    fun onEvent(event:SignupUIEvent){
        validateDataWitRules()
        when(event){
            is SignupUIEvent.NameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    name = event.name
                )
                printState()
            }
            is SignupUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
                printState()
            }
            is SignupUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
                printState()
            }
            is SignupUIEvent.RegisterButtonClicked -> {
                signUp()
            }
        }
    }

    private fun signUp(){
//        Log.d(TAG, "Inside_signUp")
//        printState()
        //validateDataWitRules()
        createUserInFirebase(
            email = registrationUIState.value.email,
            password = registrationUIState.value.password
        )

    }

    private fun validateDataWitRules() {
        val nameResult = Validator.validateName(
            name = registrationUIState.value.name
        )
        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )
        Log.d(TAG, "nameResult = $nameResult")
        Log.d(TAG, "emailResult = $emailResult")
        Log.d(TAG, "passwordResult = $passwordResult")

        registrationUIState.value = registrationUIState.value.copy(
            nameError = nameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed.value = nameResult.status && emailResult.status && passwordResult.status
    }

    private fun printState(){
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }



    private fun createUserInFirebase(email: String, password: String){
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside_OnCompleteListener")
                Log.d(TAG, "isSuccessful = ${it.isSuccessful}")

                if(it.isSuccessful){


                    val auth = FirebaseAuth.getInstance()
                    val database = Firebase.database.reference
                    val getChore = database.child("chores-count").get().addOnCompleteListener()
                    {
                        if(it.isSuccessful)
                        {
                            val today = LocalDate.now()
                            database.child("current-date").setValue(today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString())
                            val chore: Int = it.result.value.toString().toInt()
                            Log.i("firebase","${it.result.value}")
                            val usr = User(registrationUIState.value.name,registrationUIState.value.email,auth.currentUser!!.uid,0,chore)
                            val reference = database.child("users").child(usr.uid.toString())
                            reference.setValue(usr).addOnCompleteListener {
                                if(it.isSuccessful)
                                {
                                    if(chore+1<4)
                                    {
                                        database.child("chores-count").setValue(chore+1)
                                    }
                                    else
                                    {
                                        database.child("chores-count").setValue(0)
                                    }
                                    Log.i("firebase","Registered successfully")
                                    FlatAppRouter.navigateTo(Screen.HomeScreen)

                                }
                                else
                                {
                                    Log.e("firebase","Failed to add")
                                }

                            }

                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside_OnFailureListener")
                Log.d(TAG, "Exception = ${it.message}")
                Log.d(TAG, "Exception = ${it.localizedMessage}")
            }
    }

    fun logout(){
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signOut()

        val authStateListener = AuthStateListener{
            if(it.currentUser == null){
                FlatAppRouter.navigateTo(Screen.LoginScreen)
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }
}
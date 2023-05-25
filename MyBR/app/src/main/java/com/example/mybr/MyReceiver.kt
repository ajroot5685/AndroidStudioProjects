package com.example.mybr

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyReceiver : BroadcastReceiver() {

    val pattern1 = Regex("""^\d{2}/\d{2}\s([01][0-9]|2[0-3]):([0-5][0-9])\s\d{1,3}(,\d{3})*원""")
    val scope = CoroutineScope(Dispatchers.IO)
    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        scope.launch {

            if (intent.action.equals("android.provider.Telephony.SMS_RECEIVED")) {
                val msg = Telephony.Sms.Intents.getMessagesFromIntent(intent)

                val message = msg[0].messageBody
                if (message.contains("건국카드")) {
                    val tmpstr = message.split("\n")
//                  for(str in tmpstr)
                    var result = false
                    for (str in tmpstr.subList(1, tmpstr.size)) {
                        if (pattern1.containsMatchIn(str)) {
                            result = true
                            break
                        }
                    }
                    if (result) {
                        val newIntent = Intent(context, MainActivity::class.java)
                        newIntent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        newIntent.putExtra("msgSender", msg[0].originatingAddress)
                        newIntent.putExtra("msgBody", msg[0].messageBody)
                        context.startActivity(newIntent)
                    }
                }
            }
        }
        pendingResult.finish()
    }
}
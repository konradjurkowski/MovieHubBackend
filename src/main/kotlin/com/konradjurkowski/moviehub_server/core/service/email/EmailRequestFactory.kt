package com.konradjurkowski.moviehub_server.core.service.email

import com.konradjurkowski.moviehub_server.core.model.EmailRequest
import com.konradjurkowski.moviehub_server.feature.user.model.entity.User

object EmailRequestFactory {

    fun createUserActivationRequest(user: User, activationCode: String): EmailRequest {
        val content = """
        <!DOCTYPE html>
        <html lang="en">
        <body style="font-family:Arial, sans-serif; color:#333;">
	        <h3>Hi, ${user.name}!</h3>
	        <p>
    	        Thank you for signing up to <strong>MovieHub</strong>. 
    	        To complete the registration process, enter the activation code in the application.
            </p>
	        <p>Your activation code is valid for 15 minutes:</p>
            <p style="font-size:1.2em; font-weight:bold; letter-spacing:2px;">$activationCode</p>
            <p>Best regards, MovieHub Team</p>
        </body>
        </html>
        """.trimIndent()
        return EmailRequest(
            recipient = user.email,
            subject = "MovieHub - account activation",
            content = content,
        )
    }
}
package com.example.harmonyhub.ui.login
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.harmonyhub.R
import androidx.compose.ui.platform.LocalFocusManager
@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 24.dp)
            .clickable { focusManager.clearFocus() }, // Clear focus when tapping outside
        horizontalAlignment = Alignment.CenterHorizontally
    ){


        Spacer(modifier = Modifier.height(80.dp))

        // Logo
        Image(
            painter = painterResource(id = R.drawable.speaker), // Replace with your logo icon
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        // Title
        Text(
            text = "Login to your account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Email Input Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Account", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_google_48), // Replace with your email icon
                    contentDescription = "Email Icon",
                    tint = Color.Gray
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DB954),
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Password", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_google_48), // Replace with your password icon
                    contentDescription = "Password Icon",
                    tint = Color.Gray
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_eye_60),
                    contentDescription = "Toggle Password Visibility",
                    tint = Color.Gray
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DB954),
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Remember Me Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    uncheckedColor = Color.Gray,
                    checkedColor = Color(0xFF1DB954)
                )
            )
            Text(
                text = "Remember me",
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Login Button
        Button(
            onClick = { /* TODO: Handle login */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1DB954)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Log in", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Forgot Password
        ClickableText(
            text = AnnotatedString("Forgot the password?"),
            onClick = { /* TODO: Handle forgot password */ },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Or Continue With
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1f), color = Color.Gray)
            Text(
                text = " or continue with ",
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

//        // Social Media Icons
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(24.dp),
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Icon(
//                painter = painterResource(id =R.drawable.icons8_google_48), // Replace with your Google icon
//                contentDescription = "Google",
//                modifier = Modifier.size(40.dp),
//                tint = Color.Unspecified
//            )
//            Icon(
//                painter = painterResource(id = R.drawable.icons8_google_48), // Replace with your Facebook icon
//                contentDescription = "Facebook",
//                modifier = Modifier.size(40.dp),
//                tint = Color.Unspecified
//            )
//            Icon(
//                painter = painterResource(id = R.drawable.icons8_google_48), // Replace with your Apple icon
//                contentDescription = "Apple",
//                modifier = Modifier.size(40.dp),
//                tint = Color.Unspecified
//            )
//        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sign Up
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Don’t have an account?", color = Color.Gray)
            Spacer(modifier = Modifier.width(4.dp))
            ClickableText(
                text = AnnotatedString("Sign Up"),
                onClick = { /* TODO: Handle sign up */ },
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF1DB954))
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}

package br.com.hellodev.controledeprodutos.helper;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseHelper {

    private static FirebaseAuth auth;

    public static FirebaseAuth getAuth(){
         if(auth == null){
             auth = FirebaseAuth.getInstance();
         }
         return auth;
    }

    public static boolean getAutenticado(){
        return getAuth().getCurrentUser() !=null;
    }

}

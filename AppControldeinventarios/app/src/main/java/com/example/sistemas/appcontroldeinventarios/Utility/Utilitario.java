package com.example.sistemas.appcontroldeinventarios.Utility;

import java.util.Calendar;

public class Utilitario {
    Calendar calendar;
    Integer hora,minuto,tiempo;

    public String Saludo(){

        String saludo;
        calendar = Calendar.getInstance();
        hora = calendar.get(Calendar.HOUR_OF_DAY);
        minuto = calendar.get(Calendar.MINUTE);
        tiempo = hora * 60 +minuto;

        if (tiempo>360 && tiempo<720){
            saludo = "Buenos Días ";
        }else if(tiempo>=720 && tiempo<=1080){
            saludo = "Buenos Tardes ";
        }else {
            saludo = "Buenos Noches ";
        }
        return saludo;
    }




}

package com.example.pedidos.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MoneyFormatDecimal {


    private static final DecimalFormatSymbols DOLAR = new DecimalFormatSymbols(Locale.US);
    public static final DecimalFormat DINHEIRO_DOLAR = new DecimalFormat("¤ ###,###,##0.00",DOLAR);
    private static final DecimalFormatSymbols EURO = new DecimalFormatSymbols(Locale.GERMANY);

    public static final DecimalFormat DINHEIRO_EURO = new DecimalFormat("¤ ###,###,##0.00",EURO);
    private static final Locale BRAZIL = new Locale("pt","BR");
     private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRAZIL);
     public static final DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ ###,###,##0.00",REAL);


     public static String mascaraDinheiro(double valor, DecimalFormat moeda){
        return moeda.format(valor);
               }




    }

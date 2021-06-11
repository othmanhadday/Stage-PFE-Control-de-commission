package com.hadday.gestion_commission.Constante;

import java.text.DecimalFormat;

public abstract class RegleCalcul {

    public static double droitAdmissionRegle(double quantite, double prix, double taux) {
        double result = 0;
        if (taux == 0) {
            result = quantite * prix;
        }
        result = quantite * prix * taux / 100;
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(result));
    }


    public static double avoirsRegle(double quantite, double prix, double taux) {
        double result = 0;
        if (taux == 0) {
            result = quantite * prix;
        }
        result = quantite * prix * (taux / 100) / 360;
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(result));
    }

    public static double comptesRegle(double quantite, double montant) {
        double result = 0;
        result = quantite * montant;
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(result));
    }

}

package com.hadday.gestion_commission.Constante;

public abstract class RegleCalcul {

    public static double droitAdmissionRegle(double quantite, double prix, double taux) {
        if (taux == 0) {
            return quantite * prix;
        }
        return quantite * prix * taux;
    }

    public static double avoirsRegle(double quantite, double prix, double taux) {
        if (taux == 0) {
            return quantite * prix ;
        }
        return quantite * prix * (taux / 360);
    }

    public static double comptesRegle(double quantite, double montant) {

        return quantite * montant;
    }

}

package com.hadday.gestion_commission.Constante;

abstract public class Queries {

    public static final String DA_Avoirs_Query = " Select fee from InstrumentClass c " +
            " INNER JOIN c.instrumentTypes t on c = t.instrumentClass " +
            " INNER JOIN t.instrumentCategories cat on t = cat.instrumentType " +
            " INNER JOIN cat.feeRates fee on cat = fee.instrumentCategorie " +
            " where fee.feeType.categorieFees.typeCommission = :typeCommission " +
            " and c.instrementClass = :className " +
            " and t.instrumentTypeCode = :typeCode " +
            " and cat.category = :category " +
            " and fee.tauxMontant = 'T' " +
            " and c.deleted= false " +
            " and t.deleted= false " +
            " and cat.deleted= false " +
            " and fee.deleted= false ";


    public static final String Compte_Query = " Select fee from InstrumentClass c " +
            " INNER JOIN c.instrumentTypes t on c = t.instrumentClass " +
            " INNER JOIN t.instrumentCategories cat on t = cat.instrumentType " +
            " INNER JOIN cat.feeRates fee on cat = fee.instrumentCategorie " +
            " where fee.feeType.categorieFees.typeCommission = :typeCommission " +
            " and fee.feeType.typeName = :feeType " +
            " and c.instrementClass = :className " +
            " and t.instrumentTypeCode = :typeCode " +
            " and cat.category = :category " +
            " and fee.tauxMontant = 'M' " +
            " and c.deleted = false" +
            " and t.deleted = false" +
            " and cat.deleted = false " +
            " and fee.deleted = false ";

}



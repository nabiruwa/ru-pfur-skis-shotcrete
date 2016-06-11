package ru.pfur.skis;

import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Admin on 24.04.2016.
 */
public class Model {
    private static Map<String, Double> steelList = new HashMap<>(2);
    private static Map<String, Double> concreteList = new HashMap<>(2);

    static {
        //steel
        steelList.put("A400", 250.0);
        steelList.put("A240", 200.0);
        //concrete
        concreteList.put("B60", 200.0);
    }

    Double dpipe = 0.0;
    Double spipe = 0.0;
    Double mdeform = 0.0;
    Double imodulb = 0.0;
    Double imoduls = 0.0;
    Double tpp = 0.0;
    Double dspan = 0.0;
    Double cpuasb = 0.0;
    Double cpuass = 0.0;
    Double shorttermcol = 0.0;
    Double longtermcol = 0.0;
    Double selfweightcol = 0.0;
    Double shorttermplate = 0.0;
    Double longtermplate = 0.0;
    Double selfweightplate = 0.0;
    Double cover = 0.0;
    Double steel = 0.0;
    Double concrete = 0.0;
    Integer nfloors;
    Double h1Floors = 0.0;
    Double hFloors = 0.0;
    Double Ap = 0.0;
    Double Ixn = 0.0;


    public Model() {
        init();
    }

    public Boolean calculate(Stage stage) {
//   calculate N
        Double Ag = dspan * dspan;
        Double G1 = (longtermplate) * Ag * 1.3;
        Double G2 = cover * Ag * 1.3;
        Double G3 = (3.14 * dpipe * dpipe) / 4 * h1Floors * (25 / 1000000.0) * 1.3 * 1.1;
        Double G4 = (3.14 * dpipe * dpipe) / 4 * hFloors * (25 / 1000000.0) * 1.3 * 1.1;

        Double P1 = 2.4 * Ag * 1.3 / 10000.0;
        Double P2 = 1.5 * Ag * 1.3 / 10000.0;
        Double Psn = 0.7 * Ag * 1.3 / 10000.0;

        Double Const = (nfloors - 1) * G1 + G2 + G3 + (nfloors - 1) * G4;
        Double LongTerm = P1 * (nfloors - 1);
        Double ShortTerm = P2 * (nfloors - 1);

        Double N = Const + LongTerm + Psn + (ShortTerm + Psn) * 0.9;

        Double ea1 = 1.0 / 600.0 * dspan;
        Double ea2 = 1.0 / 30.0 * dpipe;

        Double ea = Math.max(ea1, ea2);

        Double M = N * (ea);
        Double b = dpipe - 2 * spipe;
        Double g = dpipe - b;
        Double Ixa = 3.14 * dpipe * dpipe * dpipe * dpipe / 64;
        Double Ixy = 3.14 * b * b * b * b / 64;
        Double Ixn = Ixa - Ixy;
        Double n = 0.13;
        Double As = (3.14 * (dpipe * dpipe - b * b)) / 4;
        Double Ab = (3.14 * (b * b)) / 4;
        Double Ap = As + n * Ab;


        if ((N / Ap + (M / Ixn) * (dpipe / 2)) < steel) {
            new AlertDialog(stage, "Прочность обеспечена!\nВот вам и N = " + N + "\nИ вот вам M = " + M, AlertDialog.ICON_INFO).showAndWait();
            return true;
        } else {
            new AlertDialog(stage, "Прочность НЕ обеспечена!\nВот вам и N = " + N + "\nИ вот вам M = " + M, AlertDialog.ICON_ERROR).showAndWait();
            return false;
        }


    }

    private void init() {

    }

    public static Set<String> getSteels() {
        return steelList.keySet();
    }

    public static Set<String> getConcretes() {
        return concreteList.keySet();
    }

    ;

    public Double getH1Floors() {
        return h1Floors;
    }

    public void setH1Floors(Double h1Floors) {
        this.h1Floors = h1Floors;
    }

    public Double gethFloors() {
        return hFloors;
    }

    public void sethFloors(Double hFloors) {
        this.hFloors = hFloors;
    }

    public static Double getSteelValue(String key) {
        return steelList.get(key);
    }

    public static Double getConcreteValue(String key) {
        return concreteList.get(key);
    }

    public Double getDpipe() {
        return dpipe;
    }

    public void setDpipe(Double dpipe) {
        this.dpipe = dpipe;
    }

    public Double getSpipe() {
        return spipe;
    }

    public void setSpipe(Double spipe) {
        this.spipe = spipe;
    }

    public Double getMdeform() {
        return mdeform;
    }

    public void setMdeform(Double Mdeform) {
        this.mdeform = Mdeform;
    }

    public Double getImodulb() {
        return imodulb;
    }

    public Double getDspan() {
        return dspan;
    }

    public Double getSteel() {
        return steel;
    }

    public void setSteel(Double steel) {
        this.steel = steel;
    }

    public Double getConcrete() {
        return concrete;
    }

    public void setConcrete(Double concrete) {
        this.concrete = concrete;
    }

    public Integer getNfloors() {
        return nfloors;
    }

    public void setNfloors(Integer nfloors) {
        this.nfloors = nfloors;
    }

    public void setImodulb(Double Imodulb) {
        this.imodulb = Imodulb;
    }

    public Double getImoduls() {
        return imoduls;
    }

    public void setImoduls(Double Imoduls) {
        this.imoduls = Imoduls;
    }

    public Double getTpp() {
        return tpp;
    }

    public void setTpp(Double Tpp) {
        this.tpp = Tpp;
    }

    public Double Dspan() {
        return dspan;
    }

    public void setDspan(Double Dspan) {
        this.dspan = Dspan;
    }

    public Double getCpuasb() {
        return cpuasb;
    }

    public void setCpuasb(Double Cpuasb) {
        this.cpuasb = Cpuasb;
    }

    public Double getCpuass() {
        return cpuass;
    }

    public void setCpuass(Double Cpuass) {
        this.cpuass = cpuass;
    }

    public Double getShorttermcol() {
        return shorttermcol;
    }

    public void setShorttermcol(Double Shorttermcol) {
        this.shorttermcol = Shorttermcol / 10000.0;
    }

    public Double getLongtermcol() {
        return longtermcol;
    }

    public void setLongtermcol(Double longtermcol) {
        this.longtermcol = longtermcol / 10000.0;
    }

    public Double getSelfweightcol() {
        return selfweightcol;
    }

    public void setSelfweightcol(Double Selfweightcol) {
        this.selfweightcol = Selfweightcol / 10000.0;
    }

    public Double getShorttermplate() {
        return shorttermplate;
    }

    public void setShorttermplate(Double Shorttermplate) {
        this.shorttermplate = Shorttermplate / 10000.0;
    }

    public Double getLongtermplate() {
        return longtermplate;
    }

    public void setLongtermplate(Double Longtermlate) {
        this.longtermplate = Longtermlate / 10000.0;
    }

    public Double getSelfweightplate() {
        return selfweightplate;
    }

    public void setSelfweightplate(Double Selfweightplate) {
        this.selfweightplate = Selfweightplate / 10000.0;
    }

    public Double getCover() {
        return cover;
    }

    public void setCover(Double cover) {
        this.cover = cover / 10000.0;
    }

    public Double getAp() {
        return Ap;
    }

    public void setAp(Double ap) {
        Ap = ap;
    }

    public Double getIxn() {
        return Ixn;
    }

    public void setIxn(Double ixn) {
        Ixn = ixn;
    }
}


package tesis1.genetic;

/**
 * Title:        Asignacion de horarios
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      ITSON
 * @author Adolfo Espinoza
 * @version 1.0
 */

public class Costo implements Cloneable{
  private int empalmesMaestros;
  private int empalmesAulas;
  private int semCarr;
  private int horContinuo;
  private int turnos;

  public void imprimeCosto() {
    System.out.println(empalmesMaestros+", "+empalmesAulas+", "+semCarr+
    ", "+horContinuo+", "+turnos+", "+
    this.getCostoDuro()+", "+this.getCostoBlando()+", "+this.getCostoTotal());
  }

   public Object clone(){
    Costo aux = new Costo();
    aux.empalmesMaestros = empalmesMaestros;
    aux.empalmesAulas=empalmesAulas;
    aux.semCarr=semCarr;
    aux.horContinuo=horContinuo;
    aux.turnos=turnos;
    return aux;
  }

  public void setEmpMaestros(int n){
    empalmesMaestros = n;
  }
  public void setEmpAulas(int n){
    empalmesAulas=n;
  }
  public void setSemCarrs(int n){
    semCarr= n;
  }
  public void setHrsContinuas(int n){
    horContinuo=n;
  }
  public void setTurnos(int n){
    turnos=n;
  }

  public int getEmpMaestros(){
    return empalmesMaestros;
  }
  public int getEmpAulas(int n){
    return empalmesAulas;
  }
  public int getSemCarrs(int n){
    return semCarr;
  }
  public int getHrsContinuas(int n){
    return horContinuo;
  }
  public int getTurnos(int n){
    return turnos;
  }

  public int getCostoDuro(){
    return  empalmesMaestros+empalmesAulas+semCarr+horContinuo;
  }
  public int getCostoBlando(){
    return turnos;
  }

  public int getCostoTotal(){
    return getCostoDuro()+getCostoBlando();
  }

}
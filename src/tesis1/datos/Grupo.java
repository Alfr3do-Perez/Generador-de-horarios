package tesis1.datos;

/**
 * Title:        Asignacion de horarios
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      ITSON
 * @author Adolfo Espinoza
 * @version 1.0
 */

public class Grupo implements Cloneable{

  private String clave;
  private int num;
  private int maestro;
  private int aula;
  private int hora;
  private int[] opcs;

  public Grupo() {

  }


  public Object clone(){
    Grupo aux = new Grupo();
    aux.setClave(new String(clave));
    aux.setNum(num);
    aux.setMaestro(maestro);
    aux.setAula(aula);
    aux.setHora(hora);
    aux.setOpcs( ((int[])opcs.clone()) );
    return aux;
  }

  public void setClave(String clve){
    clave=clve;
  }

  public String getClave(){
    return clave;
  }

  public void setNum(int n){
    num=n;
  }

  public int getNum(){
    return num;
  }

  public void setMaestro(int n){
    maestro=n;
  }
  public int getMaestro(){
    return maestro;
  }

  public void setAula(int n){
    aula=n;
  }
  public int getAula(){
    return aula;
  }

  public void setHora(int n){
    hora=n;
  }
  public int getHora(){
    return hora;
  }

  public void setOpcs(int [] n){
    opcs=n;
  }
  public int[] getOpcs(){
    return opcs;
  }

}
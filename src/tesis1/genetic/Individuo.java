package tesis1.genetic;

import tesis1.datos.*;
/**
 * Title:        Asignaci�n de horarios
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      ITSON
 * @author Adolfo Espinoza
 * @version 1.0
 */

public class Individuo implements Cloneable{
  private int [] genes;
  private int llave;
  private Costo costo = new Costo();

  private int costoDuro;//***
  private int costoBlando;//***
  private int costoTotal;//***

  public Individuo(int tamaho) {
    genes = new int[tamaho];
  }

  public Individuo() {
  }

  public Object clone(){
    Individuo aux = new Individuo();
    aux.genes=(int[])this.genes.clone();
    aux.llave=this.llave;
    aux.costo=(Costo)this.costo.clone();

    aux.costoDuro= this.costoDuro;//***
    aux.costoBlando=this.costoBlando;//***
    aux.costoTotal=this.costoTotal;//***
    return aux;
  }

  public Costo getCosto(){
    return costo;
  }

  public int [] getGenes(){
    return (int[])genes.clone();
  }

  /** almacena en el gene i el valor x */
  public void setGene(int i, int x){
    this.genes[i] = x;
  }

  private void calculaLlave(){
    llave=0;
    for(int i=0; i<genes.length; i++){
      llave+=genes[i];
    }
  }

  public int getLlave(){
    return llave;
  }
  public void setLlave(int n){
    llave=n;
  }

  public void setCosto(Costo c){
    this.costo=c;
  }
  public int getCostoDuro(){//***
    return costoDuro;
  }

  public int getCostoBlando(){//***
    return costoBlando;
  }

  public int getCostoTotal(){//***
    return costoTotal;
  }

    public void setCostoDuro(int c){//***
    costoDuro=c;
  }

  public void setCostoBlando(int c){//***
    costoBlando=c;
  }

  public void setCostoTotal(int c){//***
    costoTotal=c;
  }


  /** Encuentra el costo del individuo de acuerdo a las condiciones
   *  del Catalogo cat */
  public void evalua(Catalogo cat){

        this.calculaLlave();

        cat.borraAsignados();
        cat.asignaIndividuo( this );
        this.costoDuro = cat.aulas.getEmpalmes()+ cat.maestros.getEmpalmes()+
                          cat.semCarrs.getBloqueads()+
                          cat.maestros.getHrsContinuas();
        this.costoBlando = cat.semCarrs.costoTurnos();
        this.costoTotal = this.costoDuro + this.costoBlando;
  }

  public boolean isIgual(Individuo ind){
    int[] genesAux = ind.getGenes();
    int n = genesAux.length;

    if(this.llave!=ind.llave){return false;}
    for(int i=0; i<n; i++){
      if(this.genes[i]!=genesAux[i]){
        return false;
      }
    }
    return true;
  }



}
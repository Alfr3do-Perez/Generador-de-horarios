package tesis1.genetic;

import java.util.ArrayList;

/**
 * Title:        Asignacion de horarios
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      ITSON
 * @author Adolfo Espinoza
 * @version 1.0
 */

public class Poblacion extends ArrayList<Individuo>{

  public Poblacion() {
  }

  public void ordenaMenMayor(){// selection sort
    Individuo aux1, aux2;
    int indxMin, minimo;
    int n = this.size();
    for(int i=0; i<n-1 ; i++){
      indxMin=i;
      minimo = ((Individuo)this.get(i)).getCosto().getCostoTotal();
      for(int j=i+1; j<n; j++){
        if( ((Individuo)this.get(j)).getCosto().getCostoTotal() < minimo  ){
          indxMin = j;
          minimo = ((Individuo)this.get(j)).getCosto().getCostoTotal();
        }
      }
      if(indxMin!=i){
        aux2=(Individuo)this.get(indxMin);
        aux1=(Individuo)this.set(i,(Individuo)aux2);
        this.set(indxMin,(Individuo)aux1);
      }
    }
  }

  public void eliminaRepetidos(){
    int i=0, j;
    Individuo indivAuxI, indivAuxJ;
    boolean iguales;
    while(i<this.size()){
      indivAuxI = (Individuo)this.get(i);
      j=i+1;
      while(j<this.size()){
        iguales = true;
        indivAuxJ = (Individuo)this.get(j);
        iguales = indivAuxJ.isIgual(indivAuxI);
        if(iguales){this.remove(j);}
        else{j++;}
      }
      i++;
    }
  }

  public boolean contiene(Individuo indPrueba){
    boolean contenido=false;
    for(int i=0; i<this.size(); i++){
      if(indPrueba.isIgual((Individuo)this.get(i))){
        contenido=true;
      }
    }//for i
    return contenido;
  }

  public void info(){
    System.out.print("info pob: "+this.size()+"   ");
    for(int i=0; i<this.size(); i++){
      System.out.print(" "+((Individuo)this.get(i)).getCosto().getCostoTotal());
    }
    System.out.println();
  }

}
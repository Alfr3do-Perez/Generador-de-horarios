package tesis1.datos;

import java.io.*;
import java.util.ArrayList;

/**
 * Title:        Asignaci�n de horarios
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      ITSON
 * @author Adolfo Espinoza
 * @version 1.0
 */

public class VectorAulas extends ArrayList<Aula> implements Cloneable{

  public VectorAulas(String archAulas) {
    this.leeArchAulas(archAulas);
  }

  public VectorAulas(){
  }

    
  public Object clone(){
    VectorAulas aux = new VectorAulas();
    int nAuls=this.size();
    for(int i=0; i<nAuls; i++){
      aux.add((Aula) this.get(i).clone());
    }
    return aux;
  }

  private void leeArchAulas(String nomArch){
    int longitud;
    DataInput arch;
    AulaArch aulaEnt;
    Aula aula;
    this.removeAll(this);
    this.trimToSize();

    try{
      arch = new DataInputStream(new FileInputStream(new File(nomArch)));
      longitud =arch.readInt();   //Lee numero de aulas

      for(int i=0; i<longitud; i++){  //LEE CADA AULA
        aulaEnt=new AulaArch(); // Crea objetos Aula
        aulaEnt.cargarAula(arch);  // Asigna valores de archivo
        aula = new Aula();
        aula.copiaDesde(aulaEnt);  // copia datos de AulaEnt a Aula

        this.add(aula);      // Lo agrega al vector
      }
    }
    catch(Exception e) {
      System.out.print("\n Error leyendo archivo de datos de aulas \n");
      e.printStackTrace();
    }
  }

  public int getIndexClave(String claveBuscada){
  // Regresa el indice (int) del elemento que tiene el String claveBuscada
  // en el atributo clave.
    String claveEnVect;
    int n =this.size();
    int cont;
    for(cont=0; cont<n; cont++){
      claveEnVect = this.get(cont).getClave();
      if(claveEnVect.equals(claveBuscada))break;
    }
    if(cont==n){
      System.out.println("Aula no encontrada en el archivo tabAulas.dat");
    }
    return cont;
  }

  public int getEmpalmes(){
    int suma=0;
    for(int i=0; i<this.size(); i++){
      suma += (int)((Aula)this.get(i)).cuentaEmpalmes();
    }
    return suma;
  }

  public void desasignaTodos(){
    for(int i=0; i<this.size(); i++){
      ((Aula)this.get(i)).desasignaTodo();
    }
  }
}
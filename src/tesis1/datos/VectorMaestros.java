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

public class VectorMaestros extends ArrayList<Maestro> implements Cloneable{

  public VectorMaestros(String archMaestros) {
    this.leeArchMaestros(archMaestros);
  }

  public VectorMaestros() {
  }

  public Object clone(){
    VectorMaestros aux = new VectorMaestros();
    int nMaes=this.size();
    for(int i=0; i<nMaes; i++){
      aux.add((Maestro)this.get(i).clone());
    }
    return aux;
  }


   private void leeArchMaestros(String nomArch){
    int longitud;
    DataInput arch;
    MaestroArch maestroEnt;
    Maestro maestro;
    this.removeAll(this);
    this.trimToSize();

    try{
      arch = new DataInputStream(new FileInputStream(new File(nomArch)));
      longitud =arch.readInt();   //Lee numero de maestros

      for(int i=0; i<longitud; i++){  //LEE CADA MAESTRO
        maestroEnt=new MaestroArch(); // Crea objetos Maestro
        maestroEnt.cargarMaestro(arch);  // Asigna valores de archivo
        maestro = new Maestro();
        maestro.copiaDesde(maestroEnt);  // copia datos de MaestroEnt a Maestro

        this.add(maestro);      // Lo agrega al vector
      }

    }
    catch(IOException e) {
      System.out.print("\n Error leyendo archivo de datos de maestros \n");
      e.printStackTrace();
    }
  }

  public int getIndexClave(String claveBuscada){
    String claveEnVect;
    int n =this.size();
    int cont;
    for(cont=0; cont<n; cont++){
      claveEnVect = this.get(cont).getClave();
      claveEnVect = claveEnVect.substring(1,claveEnVect.length());

      if(claveEnVect.equals(claveBuscada))break;
    }
    if(cont==n){
      System.out.println("Maestro no encontrado en el archivo tabMaestros.dat");
      System.out.println("Clave Buscada = "+claveBuscada);
    }
    return cont;
  }


  public int getEmpalmes(){
    int suma=0;
    for(int i=0; i<this.size(); i++){
      suma += this.get(i).cuentaEmpalmes();
    }
    return suma;
  }

  /* Entrega el n�mero de horas continuas m�s de 3 que imparte un maestro*/
  public int getHrsContinuas(){
    int suma=0;
    for(int i=0; i<this.size(); i++){
      suma += this.get(i).horsContinuas(6);
    }
    return suma;
  }

  public void desasignaTodos(){
    int n=this.size();
    for(int i=0; i<n; i++){
      ((Maestro)this.get(i)).desasignaTodo();
    }
  }

}

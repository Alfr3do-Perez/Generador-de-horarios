package tesis1.datos;

import java.util.ArrayList;
import tesis1.io.*;
/**
 * Title:        Asignaci�n de horarios
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      ITSON
 * @author Adolfo Espinoza
 * @version 1.0
 */

public class VectorMaterias extends ArrayList<Materia> implements Cloneable{

  public VectorMaterias(String archMats) {
    leeArchMaterias(archMats);
  }

  private void leeArchMaterias(String arch){
    String params [];
    int numParams;
    ParamFileRead mats = new ParamFileRead(arch);
    boolean eof=false;
    while(null!=(params = mats.readParams()) ){//Mientras no termine el archivo
      numParams = params.length;
      if (numParams==4){
        Materia buff = new Materia();
        buff.setClave( params[0] );
        buff.setTipoAula( params[1] );
        params[2]=params[2].substring(0,params[2].length()-1);
        buff.setTipoHors( Long.parseLong(params[2],16) );
        buff.setDescrip( params[3] );
        this.add(buff); // agrega nueva materia al vector
      }
    }
  }

  public int getIndexClave(String claveBuscada){
    String claveEnVect;
    int n =this.size();
    int cont;
    for(cont=0; cont<n; cont++){
      claveEnVect = this.get(cont).getClave();
      if(claveEnVect.equals(claveBuscada))break;
    }
    if(cont==n){
      System.out.println("Materia "+claveBuscada+" no encontrado en el archivo tabMaterias.txt");
      System.exit(0);
    }
    return cont;
  }


}
package tesis1.io;
import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ParamFileRead {
  BufferedReader hors;

  public ParamFileRead(String arch) {
    try{
      hors = new BufferedReader(new FileReader(arch));
    }catch(IOException e){
      System.out.println("Error " + e.toString() );
    }
  }

  public String[] readParams(){
    int contParams=0;
    int lugarComa=0;
    boolean eof=true;
    boolean finLinea = false;
    String linea="";

    try{
      linea = hors.readLine();
      if(linea==null) return null;
      if(borraEspaciosExtremos(linea)=="") return null;
    }catch(IOException e){
      System.out.println("Error "+e.toString() );
    }

    String [] listaAux = new String[10]; // maximo 10 parametros por linea

    do{
      lugarComa = linea.indexOf(',');
      if(lugarComa==-1){
        lugarComa=linea.length()-1;
        finLinea=true;
      }
      if(finLinea)listaAux[contParams++]=linea.substring(0,lugarComa+1);
      else listaAux[contParams++]=linea.substring(0,lugarComa);
      linea=linea.substring(lugarComa+1,linea.length());
    }while(!finLinea);

    String[] lista = new String[contParams];
    for(int i=0; i<contParams; i++){
      lista[i]=borraEspaciosExtremos(listaAux[i]);
    }
    return lista;
  }

  private String borraEspaciosExtremos(String sucio){
    int inicio,fin;
    int longitud=sucio.length();
    for(inicio =0; inicio<longitud; inicio++)
      if( sucio.charAt(inicio) != ' ' ) break;
    for(fin =longitud; fin>=0; fin--)
      if( sucio.charAt(fin-1) != ' ' ) break;

    return sucio.substring(inicio,fin);
  }



}
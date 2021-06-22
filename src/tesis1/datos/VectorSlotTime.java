package tesis1.datos;

import java.util.ArrayList;
import tesis1.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class VectorSlotTime extends ArrayList<SlotTime> implements Cloneable{
  boolean empalmes[][]; // matriz de empalmes.

  public VectorSlotTime(String archHors) {
    leeArchSlotTimes(archHors);
    //Inicializa la matriz de empalmes
    empalmes = new boolean[this.size()][this.size()];
    for(int i=0;i<this.size();i++)for(int j=0;j<this.size();j++){
      this.empalmes[i][j] =((SlotTime)this.get(i)).isEmpalme
                    ( (SlotTime)this.get(j) );
    }

  }

  public VectorSlotTime() {
  }


  public Object clone(){
    VectorSlotTime aux = new VectorSlotTime();
    int n=this.size();
    for(int i=0; i<n; i++){
      aux.add(this.get(i));
    }
    return aux;
  }

  private void leeArchSlotTimes(String arch){
    String params [];
    int numParams;
    ParamFileRead hors = new ParamFileRead(arch);
    boolean eof=false;
    while(null!=(params = hors.readParams()) ){//Mientras no termine el archivo
      numParams = params.length;
      if (numParams==4){
        SlotTime buff = new SlotTime();
        buff.setTipo(Short.parseShort(params[0]));
        buff.setOffset(Short.parseShort(params[1]));
        buff.setDescripcion(params[3]);
        buff.setMascara(params[2]);
        buff.setTurno();
            System.out.print(this.size());System.out.print(", ");
            System.out.print(buff.getOffsetStringHora());System.out.print(buff.getDescripcion());
            System.out.print(", ");System.out.println(buff.getTurno());
        this.add(buff); // agrega nuevo SlotTime al vector
      }
    }
  }

  public int[] getHorsFacts(Materia mate, Maestro maest){
    long tipoHors = mate.getTipoHors();
    int nHors = this.size();
    int contHrsFacts=0;
    int [] horsFacts = new int[nHors];

    for(int tipo=0; tipoHors!=0; tipo++){
      if(tipoHors%2==1){
        for(int i=0; i<nHors; i++){
          if( tipo == ((SlotTime)this.get(i)).getTipo()){
          //horario v�lido para la materia.
            if(maest.isFacti((SlotTime)this.get(i))){
            //horario factible para el maestro.
              horsFacts[contHrsFacts++]=i;
            }
          }
        }
      }
      tipoHors=tipoHors>>1;
    }
    int [] arrSalida = new int[contHrsFacts];
    for(int i=0; i<contHrsFacts; i++) arrSalida[i]=horsFacts[i];
    return arrSalida;
  }

  /**
   * Entrega true si 2 elementos SlotTime indicados por su posici�n en el
   * vectorSlotTime estan empalmados
     * @param opc1
     * @param opc2
     * @return  */
  public boolean isEmpalme(int opc1, int opc2){
    return this.empalmes[opc1][opc2];
  }

  /**
   * Entrega true si un elementos SlotTime indicados por su posici�n en el
   * vectorSlotTime se empalma con un arreglo de posiciones de elementos SlotTime
     * @param opc
     * @param opcs
     * @return 
   */
  public boolean isEmpalme(int opc, int[] opcs){
    for(int i=0; i<opcs.length; i++)
      if(isEmpalme(opc,opcs[i]))return true;
    return false;
  }

}
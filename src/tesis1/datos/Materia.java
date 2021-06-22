package tesis1.datos;

/**
 * Title:        Asignacion de horarios
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      ITSON
 * @author Adolfo Espinoza
 * @version 1.0
 */

public class Materia {

  private String clave;
  private String descripcion;
  private String tipoAula;
  private long tipoHors;

  public Materia() {
  }

  public void setClave(String c){
    clave=c;
  }
  public void setDescrip(String a){
    descripcion=a;
  }
  public void setTipoAula(String a){
    tipoAula=a;
  }
  public void setTipoHors(long th){
    tipoHors = th;
  }
  public String getClave(){
    return clave;
  }
  public String getDescrip(){
    return descripcion;
  }
  public String getTipoAula(){
    return tipoAula;
  }
  public long getTipoHors(){
    return tipoHors;
  }

  public int[] getHorsFacts(VectorSlotTime horas){
    long tipoHors=this.getTipoHors();
    int nHors = horas.size();
    int contHrsFacts=0;
    int [] horsFacts = new int[nHors];

System.out.print("\n\n"+this.clave+" \n");

    for(int tipo=0; tipoHors!=0; tipo++){
      if(tipoHors%2==1){
        for(int i=0; i<nHors; i++){
          if( tipo == ((SlotTime)horas.get(i)).getTipo()){
            horsFacts[contHrsFacts++]=i;

System.out.print(((SlotTime)horas.get(i)).getOffsetStringHora()+
((SlotTime)horas.get(i)).getDescripcion()+",  ");
          }
        }
      }
      tipoHors=tipoHors>>1;
    }
    int [] arrSalida = new int[contHrsFacts];
    for(int i=0; i<contHrsFacts; i++) arrSalida[i]=horsFacts[i];
    return arrSalida;
  }

}
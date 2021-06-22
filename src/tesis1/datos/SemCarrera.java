package tesis1.datos;

import java.util.ArrayList;

/**
 * Title:        Asignaci�n de horarios
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      ITSON
 * @author Adolfo Espinoza
 * @version 1.0
 */
public class SemCarrera extends ArrayList<ElementoDeSemCarr> {
  private String nombre;
  private VectorGrupos grupos; //apuntador a vector de grupos
  private VectorSlotTime horas;
  public int[] trayectoriasValidas;
  public int[] tMatu ;
  public int[] tVesp ;
  public int totTrays = 0;
  public int traysMatut =0;//turnos
  public int traysVesper =0;//turnos

  public SemCarrera(String nom, VectorGrupos grps) {
     nombre = nom;
     grupos = grps;
  }

  public String getNombre(){
    return nombre;
  }

  public void setSlotsTime(VectorSlotTime hrs){
    horas=hrs;
  }


  public int [] calculaTraysValidas(){
    totTrays = 0;
    traysMatut=0;//turnos
    traysVesper=0;//turnos
    tMatu = new int[this.size()];
    tVesp = new int[this.size()];
    return trayectoriasValidas=calculaTraysValidas(1, new int[0]);
  }

  private int [] calculaTraysValidas( int nodoIN, int [] horsOcup){
    int [] traysValidas = new  int[this.size()];
    for(int i=0; i<this.size(); i++){

        if(((ElementoDeSemCarr)this.get(i)).in!=nodoIN)
          continue;

        int grupo=((ElementoDeSemCarr)this.get(i)).grupo;
        int hora = ((Grupo)grupos.get(grupo)).getHora();
        if(horas.isEmpalme(hora,horsOcup)) continue;

        if(((ElementoDeSemCarr)this.get(i)).out==-1){
          traysValidas[i]++;
          totTrays++;
          //Verifica si el horario es matutino o vespertino
          short turno = ((SlotTime)horas.get(hora)).getTurno();
          for(int j=0; j< horsOcup.length; j++){
            if(((SlotTime)horas.get(horsOcup[j])).getTurno()!=turno){
              turno=3;//turnos diferentes;
              break;
            }
          }
          if(turno==1) { traysMatut++;//turnos
            tMatu[i]++;
          }
          if(turno==2) { traysVesper++;//turnos
            tVesp[i]++;
          }
          continue;
        }

        //Agrega hora al vector de horas ocupadas
        int[] horsOcupAgreg = new int[horsOcup.length+1];
        for(int j=0;j<horsOcup.length; j++)horsOcupAgreg[j]=horsOcup[j];
        horsOcupAgreg[horsOcup.length]=hora;


        // Calcula trayectorias validas del nodo hijo
        int [] ramaHijo = calculaTraysValidas(
                              ((ElementoDeSemCarr)this.get(i)).out,
                              horsOcupAgreg );
        //Sumamos trayectorias de nodos hijo
        for(int j=0;j<ramaHijo.length;j++){
          traysValidas[j]=traysValidas[j]+ramaHijo[j];
          if(((ElementoDeSemCarr)this.get(j)).in==
              ((ElementoDeSemCarr)this.get(i)).out){
            traysValidas[i]+=ramaHijo[j];
          }
        }
    }//for i
    return traysValidas;
  }

  public int getBloquea(){
    int bloqueados=0, desbalances =0, aux;
    this.calculaTraysValidas();
    for(int t=0; t<trayectoriasValidas.length; t++){
      if(trayectoriasValidas[t]==0) bloqueados +=1;
      else{
        desbalances += Math.abs(trayectoriasValidas[t] -
                        totTrays/((ElementoDeSemCarr)this.get(t)).n);
      }
    }//for t
    return bloqueados /*+ desbalances*/;
  }

}


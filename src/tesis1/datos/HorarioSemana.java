package tesis1.datos;
import java.io.*;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class HorarioSemana implements Cloneable{
  private  int RENGLS=28, COLS=5;
  private short rejilla [][];

  public HorarioSemana() {
    rejilla = new short[RENGLS][COLS];
    for(int r=0; r<RENGLS; r++)for(int c=0; c<COLS; c++) rejilla[r][c]=0;
  }

  public Object clone(){
    int r, c;
    HorarioSemana aux = new HorarioSemana();
    for(r=0; r<RENGLS; r++)for(c=0; c<COLS; c++){
      aux.rejilla[r][c]=this.rejilla[r][c];
    }
    return aux;
  }

  public void set(int ren, int col, short valor){
    rejilla[ren][col]=valor;
  }

  public short get(int ren, int col){
  //entrega el valor de la celda de la rejilla indicada por los par�metros.
    return rejilla[ren][col];
  }

  public void set(SlotTime st){
  //Coloca "1�s" en las rejillas correspondientes al SlotTime "st"
    int r, c, rRejilla;
    short offset = st.getOffset();
    for(r=0; r<12; r++) for(c=0; c<5; c++){
      rRejilla=r+offset;
      if(rRejilla<RENGLS)
        rejilla[rRejilla][c] = st.getMascara(r,c);
    }
  }

  public void add(SlotTime st){
  //Le suma "1" a las rejillas correspondientes al SlotTime "st"
    int r, c, rRejilla;
    short offset = st.getOffset();
    for(r=0; r<12; r++) for(c=0; c<COLS; c++){
      rRejilla=offset+r;
      if(rRejilla<RENGLS)
        rejilla[rRejilla][c] += st.getMascara(r,c);
    }
  }

  public void subs(SlotTime st){
  //Le resta "1" a las rejillas correspondientes al SlotTime "st"
    int r, c, rRejilla;
    short offset = st.getOffset();
    for(r=0; r<12; r++) for(c=0; c<COLS; c++){
      rRejilla=offset+r;
      if(rRejilla<RENGLS)
        rejilla[rRejilla][c] -= st.getMascara(r,c);
    }
  }

  public void setHorario(HorarioSemana horEnt){
    int r, c;
    for(r=0; r<RENGLS; r++) for(c=0; c<COLS; c++){
      this.rejilla[r][c] = horEnt.rejilla[r][c];
    }
  }

  public void setHorario(short [][] horEnt){
    this.rejilla=horEnt;
  }

  public void guardarHorario(DataOutput archSalida){
  //Guarda HorarioSemana en disco
    try{
      int r, c;
      for(r=0; r<RENGLS; r++)for(c=0; c<COLS; c++){
        archSalida.writeInt(((int)this.rejilla[r][c]));
      }
    }
    catch(Exception e) {
      System.out.print("\n error de archivo salida\n");
      e.printStackTrace();
    }
  }
  public void cargarHorario(DataInput arch){
  // Retorna un objeto de la clase HorarioSemana leido del archivo
    try{
      int r, c;
      for(r=0; r<RENGLS; r++){
        for(c=0; c<COLS; c++){
          this.rejilla[r][c] = (short)arch.readInt();
        }
      }
    }
    catch(IOException e) {
      System.out.println("\n error de lectura en archivo horario\n");
      e.printStackTrace();
    }
  }
}
/* Generated By:JJTree: Do not edit this line. ASToptor.java */

package edu.vt.cs.sam.readers;

public class ASToptor extends SimpleNode {
  public ASToptor(int id) {
    super(id);
  }

  public ASToptor(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}

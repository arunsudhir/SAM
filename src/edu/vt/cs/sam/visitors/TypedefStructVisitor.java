package edu.vt.cs.sam.visitors;

import edu.vt.cs.sam.CodeHandler;
import edu.vt.cs.sam.attributes.JavaClassAttributes;
import edu.vt.cs.sam.readers.ASTclass_specifier;
import edu.vt.cs.sam.readers.ASTinit_declarator;
import edu.vt.cs.sam.readers.ASTqualified_id;

public class TypedefStructVisitor extends ClassDefinitionVisitor {

    private boolean definingName = false;
    public TypedefStructVisitor(CodeHandler handler) {
        super(handler);
    }

    @Override
    public Object visit(ASTqualified_id node, Object data) {
        if(definingName) {
            JavaClassAttributes attrs = ((JavaClassAttributes) data);
            attrs.setClassName(node.getImage());
        }
        return null;
    }

    @Override
    public Object visit(ASTinit_declarator node, Object data) {
        definingName = true;
        return super.visit(node, data);
    }
    
    @Override
    public Object visit(ASTclass_specifier node, Object data) {
        super.collectBaseClasses(node, this, (JavaClassAttributes) data);
        node.childrenAccept(this, data);
        return null;
    }
}

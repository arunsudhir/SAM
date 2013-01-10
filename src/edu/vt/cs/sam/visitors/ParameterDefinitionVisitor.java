package edu.vt.cs.sam.visitors;

import edu.vt.cs.sam.readers.*;
import edu.vt.cs.sam.CodeHandler;
import edu.vt.cs.sam.attributes.JavaMethodAttributes;

public class ParameterDefinitionVisitor extends CPPParserVisitorAdapter {

    public ParameterDefinitionVisitor(CodeHandler handler) {
        super(handler);
    }
    
    @Override
    public Object visit(ASTqualified_id node, Object data) {

        ((JavaMethodAttributes) data).addParameter(node.getImage());
        return null;
    }

    @Override
    public Object visit(ASTtype_qualifier node, Object data) {

        try {
            ((JavaMethodAttributes) data).addParameter(node.getImage());
        } catch (ArrayIndexOutOfBoundsException ex) {
            ((SimpleNode) node.jjtGetParent()).dump(">>>");
        }
        return null;
    }

    @Override
    public Object visit(ASTbuiltin_type_specifier node, Object data) {

        ((JavaMethodAttributes) data).addParameter(node.getImage());
        return null;
    }

    @Override
    public Object visit(ASTptr_operator node, Object data) {

        ((JavaMethodAttributes) data).addParameter(node.getImage());
        return null;
    }

    @Override
    public Object visit(ASTclass_specifier node, Object data) {
        ((JavaMethodAttributes) data).addParameter(node.getImage());
        return null;
    }
}
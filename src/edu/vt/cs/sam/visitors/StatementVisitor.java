package edu.vt.cs.sam.visitors;

import edu.vt.cs.sam.CodeHandler;
import edu.vt.cs.sam.readers.*;

public class StatementVisitor extends CPPParserVisitorAdapter {

    protected int hideExpressionCount = 0;
    protected boolean lineEnded = false;
    public StatementVisitor(CodeHandler handler) {
        super(handler);
    }
    
    @Override
    public Object visit(ASTlabeled_statement node, Object data) {
        return acceptNode(node, data);
    }

    @Override
    public Object visit(ASTcompound_statement node, Object data) {
        return super.visit(node, data);
    }

    @Override
    public Object visit(ASTselection_statement node, Object data) {        
        ++hideExpressionCount;
        int beginLine = node.beginLine;
        int beginColumn = node.beginColumn;
        // next 2 lines look wrong, but are there b/c the selection statement
        // includes its body
        int endLine = node.beginLine;     
        int endColumn = node.beginColumn;
        for(int i = 0; i < node.jjtGetNumChildren(); ++i) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);
            if(child instanceof ASTexpression) {
                endLine = Math.max(endLine, child.endLine);
                if(endLine == child.endLine) {
                    endColumn = Math.max(endColumn, child.endColumn);
                }
            }
        }
        SimpleNode newNode = new SimpleNode(node.getNodeId());
        newNode.beginColumn = beginColumn;
        newNode.beginLine = beginLine;
        newNode.endColumn = endColumn;
        newNode.endLine = endLine;
        CPPParser.endLine(newNode);
        Object retVal = null;
        for(int i = 0; i < node.jjtGetNumChildren(); ++i) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);
            if(!(child instanceof ASTexpression)) {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
        }
        return retVal;
    }

    @Override
    public Object visit(ASTelse_statement arg0, Object arg1) {
        SimpleNode newNode = new SimpleNode(arg0.getNodeId());
        SimpleNode firstChild = (SimpleNode) arg0.jjtGetChild(0);
        newNode.beginColumn = arg0.beginColumn;
        newNode.beginLine = arg0.beginLine;
        if(arg0.beginLine < arg0.endLine) {
            newNode.endLine = Math.max(arg0.beginLine, firstChild.beginLine-1);
            newNode.endColumn = Math.max(arg0.beginLine + "else".length(), firstChild.beginColumn-1);
        } else {
            newNode.endLine = arg0.endLine;
            newNode.endColumn = arg0.endColumn;
        }
        CPPParser.endLine(newNode);
        return super.visit(arg0, arg1);
    }
    
    

    @Override
    public Object visit(ASTiteration_statement node, Object data) {
        if("do".equals(node.getImage()) || "for".equals(node.getImage())) {
            return node.childrenAccept(new IterationVisitor(handler, this), data);
        }
        ++hideExpressionCount;
        return super.visit(node, data);
    }

    @Override
    public Object visit(ASTjump_statement node, Object data) {
        return acceptNode(node, data);
    }

    @Override
    public Object visit(ASTtry_block node, Object data) {
        return super.visit(node, data);
    }

    @Override
    public Object visit(ASThandler node, Object data) {
        return super.visit(node, data);
    }

    @Override
    public Object visit(ASTthrow_statement node, Object data) {
        return acceptNode(node, data);
    }

    @Override
    public Object visit(ASTexpression node, Object data) {
        return acceptNode(node, data);
    }

    protected Object acceptNode(SimpleNode node, Object data) {
        boolean endingLine = false;
        if (!lineEnded) {
            CPPParser.endLine(node);
            lineEnded = true;
            endingLine = true;
        }
        Object retVal = super.visit(node, data);
        if (endingLine) {
            lineEnded = false;
        }
        return retVal;
    }
    
    private class IterationVisitor extends CPPParserVisitorAdapter {

        protected StatementVisitor parent;
        public IterationVisitor(CodeHandler handler, StatementVisitor parent) {
            super(handler);
            this.parent = parent;
        }
        
        @Override
        public Object visit(ASTstatement node, Object data) {
            return parent.visit(node, data);
        }
        
        @Override
        public Object visit(ASTexpression node, Object data) {
            return null;
        }
    }
}

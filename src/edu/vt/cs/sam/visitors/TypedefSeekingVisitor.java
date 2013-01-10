package edu.vt.cs.sam.visitors;

import edu.vt.cs.sam.readers.*;

public class TypedefSeekingVisitor implements CPPParserVisitor {

    protected boolean typedef = false;

    public boolean isTypedef() {
        return typedef;
    }

    public void setTypedef(boolean typedef) {
        this.typedef = typedef;
    }

    public Object visit(SimpleNode node, Object data) {
        return null;
    }

    public Object visit(ASTtranslation_unit node, Object data) {
        return null;
    }

    public Object visit(ASTexternal_declaration node, Object data) {
        return null;
    }

    public Object visit(ASTfunction_definition node, Object data) {
        return null;
    }

    public Object visit(ASTfunc_decl_def node, Object data) {
        return null;
    }

    public Object visit(ASTlinkage_specification node, Object data) {
        return null;
    }

    public Object visit(ASTdeclaration node, Object data) {
        return null;
    }

    public Object visit(ASTtype_modifiers node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTdeclaration_specifiers node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTsimple_type_specifier node, Object data) {
        return null;
    }

    public Object visit(ASTscope_override_lookahead node, Object data) {
        return null;
    }

    public Object visit(ASTscope_override node, Object data) {
        return null;
    }

    public Object visit(ASTqualified_id node, Object data) {
        return null;
    }

    public Object visit(ASTptr_to_member node, Object data) {
        return null;
    }

    public Object visit(ASTqualified_type node, Object data) {
        return null;
    }

    public Object visit(ASTtype_qualifier node, Object data) {
        return null;
    }

    public Object visit(ASTstorage_class_specifier node, Object data) {
        typedef = (node.getImage().equals("typedef"));
        return null;
    }

    public Object visit(ASTbuiltin_type_specifier node, Object data) {
        return null;
    }

    public Object visit(ASTinit_declarator_list node, Object data) {
        return null;
    }

    public Object visit(ASTinit_declarator node, Object data) {
        return null;
    }

    public Object visit(ASTclass_head node, Object data) {
        return null;
    }

    public Object visit(ASTclass_specifier node, Object data) {
        return null;
    }

    public Object visit(ASTbase_clause node, Object data) {
        return null;
    }

    public Object visit(ASTbase_specifier node, Object data) {
        return null;
    }

    public Object visit(ASTaccess_specifier node, Object data) {
        return null;
    }

    public Object visit(ASTmember_declaration node, Object data) {
        return null;
    }

    public Object visit(ASTmember_declarator_list node, Object data) {
        return null;
    }

    public Object visit(ASTmember_declarator node, Object data) {
        return null;
    }

    public Object visit(ASTconversion_function_decl_or_def node, Object data) {
        return null;
    }

    public Object visit(ASTenum_specifier node, Object data) {
        return null;
    }

    public Object visit(ASTenumerator_list node, Object data) {
        return null;
    }

    public Object visit(ASTenumerator node, Object data) {
        return null;
    }

    public Object visit(ASTptr_operator node, Object data) {
        return null;
    }

    public Object visit(ASTcv_qualifier_seq node, Object data) {
        return null;
    }

    public Object visit(ASTdeclarator node, Object data) {
        return null;
    }

    public Object visit(ASTdirect_declarator node, Object data) {
        return null;
    }

    public Object visit(ASTdeclarator_suffixes node, Object data) {
        return null;
    }

    public Object visit(ASTfunction_declarator_lookahead node, Object data) {
        return null;
    }

    public Object visit(ASTfunction_declarator node, Object data) {
        return null;
    }

    public Object visit(ASTfunction_direct_declarator node, Object data) {
        return null;
    }

    public Object visit(ASTdtor_ctor_decl_spec node, Object data) {
        return null;
    }

    public Object visit(ASTdtor_definition node, Object data) {
        return null;
    }

    public Object visit(ASTctor_definition node, Object data) {
        return null;
    }

    public Object visit(ASTctor_declarator_lookahead node, Object data) {
        return null;
    }

    public Object visit(ASTctor_declarator node, Object data) {
        return null;
    }

    public Object visit(ASTctor_initializer node, Object data) {
        return null;
    }

    public Object visit(ASTsuperclass_init node, Object data) {
        return null;
    }

    public Object visit(ASTdtor_declarator node, Object data) {
        return null;
    }

    public Object visit(ASTsimple_dtor_declarator node, Object data) {
        return null;
    }

    public Object visit(ASTparameter_list node, Object data) {
        return null;
    }

    public Object visit(ASTparameter_declaration_list node, Object data) {
        return null;
    }

    public Object visit(ASTparameter_declaration node, Object data) {
        return null;
    }

    public Object visit(ASTinitializer node, Object data) {
        return null;
    }

    public Object visit(ASTtype_name node, Object data) {
        return null;
    }

    public Object visit(ASTabstract_declarator node, Object data) {
        return null;
    }

    public Object visit(ASTabstract_declarator_suffix node, Object data) {
        return null;
    }

    public Object visit(ASTtemplate_head node, Object data) {
        return null;
    }

    public Object visit(ASTtemplate_parameter_list node, Object data) {
        return null;
    }

    public Object visit(ASTtemplate_parameter node, Object data) {
        return null;
    }

    public Object visit(ASTtemplate_id node, Object data) {
        return null;
    }

    public Object visit(ASTtemplate_argument_list node, Object data) {
        return null;
    }

    public Object visit(ASTtemplate_argument node, Object data) {
        return null;
    }

    public Object visit(ASTstatement_list node, Object data) {
        return null;
    }

    public Object visit(ASTstatement node, Object data) {
        return null;
    }

    public Object visit(ASTlabeled_statement node, Object data) {
        return null;
    }

    public Object visit(ASTcompound_statement node, Object data) {
        return null;
    }

    public Object visit(ASTselection_statement node, Object data) {
        return null;
    }

    public Object visit(ASTiteration_statement node, Object data) {
        return null;
    }

    public Object visit(ASTjump_statement node, Object data) {
        return null;
    }

    public Object visit(ASTtry_block node, Object data) {
        return null;
    }

    public Object visit(ASThandler node, Object data) {
        return null;
    }

    public Object visit(ASTexception_declaration node, Object data) {
        return null;
    }

    public Object visit(ASTthrow_statement node, Object data) {
        return null;
    }

    public Object visit(ASTexpression node, Object data) {
        return null;
    }

    public Object visit(ASTassignment_expression node, Object data) {
        return null;
    }

    public Object visit(ASTconditional_expression node, Object data) {
        return null;
    }

    public Object visit(ASTconstant_expression node, Object data) {
        return null;
    }

    public Object visit(ASTlogical_or_expression node, Object data) {
        return null;
    }

    public Object visit(ASTlogical_and_expression node, Object data) {
        return null;
    }

    public Object visit(ASTinclusive_or_expression node, Object data) {
        return null;
    }

    public Object visit(ASTexclusive_or_expression node, Object data) {
        return null;
    }

    public Object visit(ASTand_expression node, Object data) {
        return null;
    }

    public Object visit(ASTequality_expression node, Object data) {
        return null;
    }

    public Object visit(ASTrelational_expression node, Object data) {
        return null;
    }

    public Object visit(ASTshift_expression node, Object data) {
        return null;
    }

    public Object visit(ASTadditive_expression node, Object data) {
        return null;
    }

    public Object visit(ASTmultiplicative_expression node, Object data) {
        return null;
    }

    public Object visit(ASTpm_expression node, Object data) {
        return null;
    }

    public Object visit(ASTcast_expression node, Object data) {
        return null;
    }

    public Object visit(ASTunary_expression node, Object data) {
        return null;
    }

    public Object visit(ASTnew_expression node, Object data) {
        return null;
    }

    public Object visit(ASTnew_type_id node, Object data) {
        return null;
    }

    public Object visit(ASTnew_declarator node, Object data) {
        return null;
    }

    public Object visit(ASTdirect_new_declarator node, Object data) {
        return null;
    }

    public Object visit(ASTnew_initializer node, Object data) {
        return null;
    }

    public Object visit(ASTdelete_expression node, Object data) {
        return null;
    }

    public Object visit(ASTunary_operator node, Object data) {
        return null;
    }

    public Object visit(ASTpostfix_expression node, Object data) {
        return null;
    }

    public Object visit(ASTid_expression node, Object data) {
        return null;
    }

    public Object visit(ASTprimary_expression node, Object data) {
        return null;
    }

    public Object visit(ASTexpression_list node, Object data) {
        return null;
    }

    public Object visit(ASTconstant node, Object data) {
        return null;
    }

    public Object visit(ASToptor node, Object data) {
        return null;
    }

    public Object visit(ASTexception_spec node, Object data) {
        return null;
    }

    public Object visit(ASTexception_list node, Object data) {
        return null;
    }

    public Object visit(ASTelse_statement arg0, Object arg1) {
        return null;
    }

    public Object visit(ASTtemplate_default node, Object data) {
        return null;
    }

    public Object visit(ASTpreprocessor_output node, Object data) {
        return null;
    }

    public Object visit(ASTusing_namespace node, Object data) {
        return null;
    }

    public Object visit(ASTnamespace_decl node, Object data) {
        return node.childrenAccept(this, data);
    }
}

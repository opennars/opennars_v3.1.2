package com.googlecode.opennars.parser.loan.Loan.Absyn; // Java Package generated by the BNF Converter.

public class SentGoal extends Sentence {
  public final Stm stm_;
  public final TruthValue truthvalue_;
  public final Budget budget_;

  public SentGoal(Stm p1, TruthValue p2, Budget p3) { stm_ = p1; truthvalue_ = p2; budget_ = p3; }

  public <R,A> R accept(com.googlecode.opennars.parser.loan.Loan.Absyn.Sentence.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.googlecode.opennars.parser.loan.Loan.Absyn.SentGoal) {
      com.googlecode.opennars.parser.loan.Loan.Absyn.SentGoal x = (com.googlecode.opennars.parser.loan.Loan.Absyn.SentGoal)o;
      return this.stm_.equals(x.stm_) && this.truthvalue_.equals(x.truthvalue_) && this.budget_.equals(x.budget_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(37*(this.stm_.hashCode())+this.truthvalue_.hashCode())+this.budget_.hashCode();
  }


}
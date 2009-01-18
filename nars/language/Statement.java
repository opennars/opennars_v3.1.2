/*
 * Statement.java
 *
 * Copyright (C) 2008  Pei Wang
 *
 * This file is part of Open-NARS.
 *
 * Open-NARS is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Open-NARS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Open-NARS.  If not, see <http://www.gnu.org/licenses/>.
 */
package nars.language;

import java.util.ArrayList;

import nars.inference.TemporalRules;
import nars.io.Symbols;

/**
 * A statement is a compound term, consisting of a subject, a predicate,
 * and a relation symbol in between. It can be of either first-order or higher-order.
 */
public abstract class Statement extends CompoundTerm {

    /**
     * Default constructor
     */
    protected Statement() {
    }

    /**
     * Constructor with partial values, called by make
     * @param n The name of the term
     * @param arg The component list of the term
     */
    protected Statement(String n, ArrayList<Term> arg) {
        super(n, arg);
    }

    /**
     * Constructor with full values, called by clone
     * @param n The name of the term
     * @param cs Component list
     * @param open Open variable list
     * @param i Syntactic complexity of the compound
     */
    protected Statement(String n, ArrayList<Term> cs, ArrayList<Variable> open, short i) {
        super(n, cs, open, i);
    }

    /**
     * Make a Statement from String, called by StringParser
     * @param relation The relation String
     * @param subject The first component
     * @param predicate The second component
     * @return The Statement built
     */
    public static Statement make(String relation, Term subject, Term predicate) {
        if (invalidStatement(subject, predicate)) {
            return null;
        }
        if (relation.equals(Symbols.INHERITANCE_RELATION)) {
            return Inheritance.make(subject, predicate);
        }
        if (relation.equals(Symbols.SIMILARITY_RELATION)) {
            return Similarity.make(subject, predicate);
        }
        if (relation.equals(Symbols.INSTANCE_RELATION)) {
            return Instance.make(subject, predicate);
        }
        if (relation.equals(Symbols.PROPERTY_RELATION)) {
            return Property.make(subject, predicate);
        }
        if (relation.equals(Symbols.INSTANCE_PROPERTY_RELATION)) {
            return InstanceProperty.make(subject, predicate);
        }
        if (relation.equals(Symbols.IMPLICATION_RELATION)) {
            return Implication.make(subject, predicate);
        }
        if (relation.equals(Symbols.EQUIVALENCE_RELATION)) {
            return Equivalence.make(subject, predicate);
        }
        if (relation.equals(Symbols.IMPLICATION_AFTER_RELATION)) {
            return ImplicationAfter.make(subject, predicate);
        }
        if (relation.equals(Symbols.IMPLICATION_WHEN_RELATION)) {
            return ImplicationWhen.make(subject, predicate);
        }
        if (relation.equals(Symbols.IMPLICATION_BEFORE_RELATION)) {
            return ImplicationBefore.make(subject, predicate);
        }
        if (relation.equals(Symbols.EQUIVALENCE_AFTER_RELATION)) {
            return EquivalenceAfter.make(subject, predicate);
        }
        if (relation.equals(Symbols.EQUIVALENCE_WHEN_RELATION)) {
            return EquivalenceWhen.make(subject, predicate);
        }
        return null;
    }

    /**
     * Make a Statement from given components, called by the rules
     * @return The Statement built
     * @param subj The first component
     * @param pred The second component
     * @param statement A sample statement providing the class type
     */
    public static Statement make(Statement statement, Term subj, Term pred) {
        if (statement instanceof Inheritance) {
            return Inheritance.make(subj, pred);
        }
        if (statement instanceof Similarity) {
            return Similarity.make(subj, pred);
        }
        if (statement instanceof ImplicationBefore) {
            return ImplicationBefore.make(subj, pred);
        }
        if (statement instanceof ImplicationWhen) {
            return ImplicationWhen.make(subj, pred);
        }
        if (statement instanceof ImplicationAfter) {
            return ImplicationAfter.make(subj, pred);
        }
        if (statement instanceof Implication) {
            return Implication.make(subj, pred);
        }
        if (statement instanceof EquivalenceWhen) {
            return EquivalenceWhen.make(subj, pred);
        }
        if (statement instanceof EquivalenceAfter) {
            return EquivalenceAfter.make(subj, pred);
        }
        if (statement instanceof Equivalence) {
            return Equivalence.make(subj, pred);
        }
        return null;
    }

    /**
     * Make a Statement from given components and temporal information, called by the rules
     * @param statement A sample statement providing the class type
     * @param subj The first component
     * @param pred The second component
     * @param order The temporal order of the statement
     * @return The Statement built
     */
    public static Statement make(Statement statement, Term subj, Term pred, TemporalRules.Relation order) {
        if (order == TemporalRules.Relation.NONE) {
            return make(statement, subj, pred);
        }
        if (order == TemporalRules.Relation.AFTER) {
            if (statement instanceof Implication) {
                return ImplicationAfter.make(subj, pred);
            }
            if (statement instanceof Equivalence) {
                return EquivalenceAfter.make(subj, pred);
            }
            return null;
        }
        if (order == TemporalRules.Relation.WHEN) {
            if (statement instanceof Implication) {
                return ImplicationWhen.make(subj, pred);
            }
            if (statement instanceof Equivalence) {
                return EquivalenceWhen.make(subj, pred);
            }
            return null;
        }
        if (order == TemporalRules.Relation.BEFORE) {
            if (statement instanceof Implication) {
                return ImplicationBefore.make(subj, pred);
            }
            if (statement instanceof Equivalence) {
                return EquivalenceAfter.make(pred, subj);
            }
            return null;
        }
        return null;
    }

    /**
     * Make a symmetric Statement from given components and temporal information, called by the rules
     * @param statement A sample asymmetric statement providing the class type
     * @param subj The first component
     * @param pred The second component
     * @param order The temporal order of the statement
     * @return The Statement built
     */
    public static Statement makeSym(Statement statement, Term subj, Term pred, TemporalRules.Relation order) {
        if (order == TemporalRules.Relation.NONE) {
            if (statement instanceof Inheritance) {
                return Similarity.make(subj, pred);
            }
            if (statement instanceof Implication) {
                return Equivalence.make(subj, pred);
            }
            return null;
        }
        if (order == TemporalRules.Relation.AFTER) {
            if (statement instanceof Implication) {
                return EquivalenceAfter.make(subj, pred);
            }
            return null;
        }
        if (order == TemporalRules.Relation.WHEN) {
            if (statement instanceof Implication) {
                return EquivalenceWhen.make(subj, pred);
            }
            return null;
        }
        if (order == TemporalRules.Relation.BEFORE) {
            if (statement instanceof Implication) {
                return EquivalenceAfter.make(pred, subj);
            }
            return null;
        }
        return null;
    }

    /**
     * Check Statement relation symbol, called in StringPaser
     * @param s0 The String to be checked
     * @return if the given String is a relation symbol
     */
    public static boolean isRelation(String s0) {
        String s = s0.trim();
        if (s.length() != 3) {
            return false;
        }
        return (s.equals(Symbols.INHERITANCE_RELATION) ||
                s.equals(Symbols.SIMILARITY_RELATION) ||
                s.equals(Symbols.INSTANCE_RELATION) ||
                s.equals(Symbols.PROPERTY_RELATION) ||
                s.equals(Symbols.INSTANCE_PROPERTY_RELATION) ||
                s.equals(Symbols.IMPLICATION_RELATION) ||
                s.equals(Symbols.EQUIVALENCE_RELATION) ||
                s.equals(Symbols.IMPLICATION_AFTER_RELATION) ||
                s.equals(Symbols.IMPLICATION_WHEN_RELATION) ||
                s.equals(Symbols.IMPLICATION_BEFORE_RELATION) ||
                s.equals(Symbols.EQUIVALENCE_WHEN_RELATION) ||
                s.equals(Symbols.EQUIVALENCE_AFTER_RELATION));
    }

    /**
     * Override the default in making the name of the current term from existing fields
     * @return the name of the term
     */
    @Override
    protected String makeName() {
        return makeStatementName(getSubject(), operator(), getPredicate());
    }

    /**
     * Default method to make the name of an image term from given fields
     * @param subject The first component
     * @param predicate The second component
     * @param relation The relation operator
     * @return The name of the term
     */
    protected static String makeStatementName(Term subject, String relation, Term predicate) {
        StringBuffer name = new StringBuffer();
        name.append(Symbols.STATEMENT_OPENER);
        name.append(subject.getName());
        name.append(' ' + relation + ' ');
        name.append(predicate.getName());
        name.append(Symbols.STATEMENT_CLOSER);
        return name.toString();
    }

    /**
     * Check the validity of a potential Statement. [To be refined]
     * <p>
     * Minimum requirement: the two terms cannot be the same, or containing each other as component
     * @param subject The first component
     * @param predicate The second component
     * @return Whether The Statement is invalid
     */
    public static boolean invalidStatement(Term subject, Term predicate) {
        if (subject.equals(predicate)) {
            return true;
        }
        if ((subject instanceof CompoundTerm) && ((CompoundTerm) subject).containComponent(predicate)) {
            return true;
        }
        if ((predicate instanceof CompoundTerm) && ((CompoundTerm) predicate).containComponent(subject)) {
            return true;
        }
        return false;
    }
    
    /**
     * Check the validity of a potential Statement. [To be refined]
     * <p>
     * Minimum requirement: the two terms cannot be the same, or containing each other as component
     * @return Whether The Statement is invalid
     */
    public boolean invalid() {
        return invalidStatement(getSubject(), getPredicate());
    }

    /**
     * Return the first component of the statement
     * @return The first component
     */
    public Term getSubject() {
        return components.get(0);
    }

    /**
     * Return the second component of the statement
     * @return The second component
     */
    public Term getPredicate() {
        return components.get(1);
    }
}

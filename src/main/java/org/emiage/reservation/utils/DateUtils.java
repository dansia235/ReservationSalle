/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.utils;

/**
 *
 * @author Dansia
 */

import java.util.Date;

/**
 * Classe utilitaire pour les opérations liées aux dates.
 */
public class DateUtils {

    /**
     * Vérifie si deux intervalles de dates se chevauchent.
     *
     * @param start1 Date de début du premier intervalle
     * @param end1   Date de fin du premier intervalle
     * @param start2 Date de début du second intervalle
     * @param end2   Date de fin du second intervalle
     * @return true si les intervalles se chevauchent, sinon false
     */
    public static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && start2.before(end1);
    }
}
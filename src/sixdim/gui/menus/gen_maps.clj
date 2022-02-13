(ns sixdim.gui.menus.gen_maps
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms]
    [sixdim.gui.menus.switch :as menu_switch]
    [sixdim.score.swaps.gen_maps :as swaps_gen_maps]
    )
  (:gen-class))

(def key_menu {
    "t" {:log1 "reset menu to base"
         :action #(menu_switch/to_menu "base")}


    "n" {:log1  "all eights"
    :action #(do
              (swaps_gen_maps/reset_eight_gen_maps_with_active_gen_filt)
              (menu_switch/to_menu "base"))}
    "l" {:log1  "all triplets"
    :action #(do
              (swaps_gen_maps/reset_triplet_gen_maps_with_active_gen_filt)
              (menu_switch/to_menu "base"))}
    "enter" {:log1  "all sixteens"
    :action #(do
              (swaps_gen_maps/reset_sixteen_gen_maps_with_active_gen_filt)
              (menu_switch/to_menu "base"))}


    "." {:log1  "first eight"
    :action #(do
              (swaps_gen_maps/first_eight_gen_maps)
              (menu_switch/to_menu "base"))}
    "#" {:log1  "first triplet"
    :action #(do
              (swaps_gen_maps/first_triplet_gen_maps)
              (menu_switch/to_menu "base"))}
    "?" {:log1  "first sixteen"
    :action #(do
              (swaps_gen_maps/first_sixteen_gen_maps)
              (menu_switch/to_menu "base"))}



    "e" {:log1  "every 2 eight"
    :action #(do
              (swaps_gen_maps/ev2_eight_gen_maps)
              (menu_switch/to_menu "base"))}
    "i" {:log1  "every 3 eight"
    :action #(do
              (swaps_gen_maps/ev3_eight_gen_maps)
              (menu_switch/to_menu "base"))}
    "o" {:log1  "every 4 eight"
    :action #(do
              (swaps_gen_maps/ev4_eight_gen_maps)
              (menu_switch/to_menu "base"))}



    "," {:log1  "every 2 m1 eight"
    :action #(do
              (swaps_gen_maps/ev2_m1_eight_gen_maps)
              (menu_switch/to_menu "base"))}
    "-" {:log1  "every 3 m1 eight"
    :action #(do
              (swaps_gen_maps/ev3_m1_eight_gen_maps)
              (menu_switch/to_menu "base"))}
    "_" {:log1  "every 4 m1 eight"
    :action #(do
              (swaps_gen_maps/ev4_m1_eight_gen_maps)
              (menu_switch/to_menu "base"))}




    "u" {:log1  "every 2 triplet"
    :action #(do
              (swaps_gen_maps/ev2_triplet_gen_maps)
              (menu_switch/to_menu "base"))}
    "y" {:log1  "every 3 triplet"
    :action #(do
              (swaps_gen_maps/ev3_triplet_gen_maps)
              (menu_switch/to_menu "base"))}
    "h" {:log1  "every 4 triplet"
    :action #(do
              (swaps_gen_maps/ev4_triplet_gen_maps)
              (menu_switch/to_menu "base"))}



    "~" {:log1  "every 2 m1 triplet"
    :action #(do
              (swaps_gen_maps/ev2_m1_triplet_gen_maps)
              (menu_switch/to_menu "base"))}
    "$" {:log1  "every 3 m1 triplet"
    :action #(do
              (swaps_gen_maps/ev3_m1_triplet_gen_maps)
              (menu_switch/to_menu "base"))}
    "@" {:log1  "every 4 m1 triplet"
    :action #(do
              (swaps_gen_maps/ev4_m1_triplet_gen_maps)
              (menu_switch/to_menu "base"))}



    "backspace" {:log1  "every 2 sixteen"
    :action #(do
              (swaps_gen_maps/ev2_sixteen_gen_maps)
              (menu_switch/to_menu "base"))}
    " " {:log1  "every 3 sixteen"
    :action #(do
              (swaps_gen_maps/ev3_sixteen_gen_maps)
              (menu_switch/to_menu "base"))}
    "tab" {:log1  "every 4 sixteen"
    :action #(do
              (swaps_gen_maps/ev4_sixteen_gen_maps)
              (menu_switch/to_menu "base"))}



    "!" {:log1  "every 2 m1 sixteen"
    :action #(do
              (swaps_gen_maps/ev2_m1_sixteen_gen_maps)
              (menu_switch/to_menu "base"))}
    "/" {:log1  "every 3 m1 sixteen"
    :action #(do
              (swaps_gen_maps/ev3_m1_sixteen_gen_maps)
              (menu_switch/to_menu "base"))}
    "\\" {:log1  "every 4 m1 sixteen"
    :action #(do
              (swaps_gen_maps/ev4_m1_sixteen_gen_maps)
              (menu_switch/to_menu "base"))}




    "a" {:log1  "complement eight"
    :action #(do
              (swaps_gen_maps/complement_eight_gen_maps)
              (menu_switch/to_menu "base"))}
    "r" {:log1  "complement triplet"
    :action #(do
              (swaps_gen_maps/complement_triplet_gen_maps)
              (menu_switch/to_menu "base"))}
    "s" {:log1  "complement sixteen"
    :action #(do
              (swaps_gen_maps/complement_sixteen_gen_maps)
              (menu_switch/to_menu "base"))}


      })





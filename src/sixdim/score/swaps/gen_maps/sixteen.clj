(ns sixdim.score.swaps.gen_maps.sixteen
  (:use overtone.core)
  (:require
    [sixdim.state_defs :as state_defs]
    [sixdim.atoms :as atoms]
    [sixdim.common_fns :as common_fns]
    [sixdim.score.swaps.gen_maps.core :as gmc]
    [sixdim.score.score :as score]
    )
  (:gen-class))

(defn fill_sixteen_gen_maps_with_active_gen_filt []
"fill a gen_map in selection bar/sixteen boundary 
   with current active generator and filter"
  (let [;sixteen gen maps
        all_bar_maps score/all_bar_sixteens
        get_start_bar_maps score/mapping_selection_start_bar_sixteens 
        get_end_bar_maps score/mapping_selection_end_bar_sixteens 
        ;active generator and filter
        active_generator @atoms/active_generator
        active_filter @atoms/active_filter
        active_scale @atoms/active_scale
        ; bounds from active selection
        start_bar @atoms/selection_bar_start
        end_bar @atoms/selection_bar_end
        start_sixteen @atoms/selection_sixteen_start
        end_sixteen @atoms/selection_sixteen_end
        bars_inside_range (range (+ 1 start_bar) end_bar)]    

  (reset! atoms/gen_maps
  (distinct 
  (reduce into [] [

    (as-> start_bar v
      (gmc/add_bars_to_gen_maps
        (get_start_bar_maps (str start_sixteen)) [v])
      (gmc/add_gen_to_gen_maps active_generator v)
      (gmc/add_filt_to_gen_maps active_filter v)
      (gmc/add_scale_to_gen_maps active_scale v)
      (vec v))

    (as-> bars_inside_range v
      (gmc/add_bars_to_gen_maps all_bar_maps v)
      (gmc/add_gen_to_gen_maps active_generator v)
      (gmc/add_filt_to_gen_maps active_filter v)
      (gmc/add_scale_to_gen_maps active_scale v)
      (vec v))

    (as-> end_bar v
      (gmc/add_bars_to_gen_maps
        (get_end_bar_maps (str end_sixteen)) [v])
      (gmc/add_gen_to_gen_maps active_generator v)
      (gmc/add_filt_to_gen_maps active_filter v)
      (gmc/add_scale_to_gen_maps active_scale v)
      (vec v))
    ])))))


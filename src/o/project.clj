(defproject insane-noises "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [overtone/overtone "0.10.3"]]
  :repl-options {
             ;; If nREPL takes too long to load it may timeout,
             ;; increase this to wait longer before timing out.
             ;; Defaults to 30000 (30 seconds)
             :timeout 180000
             :host "127.0.0.1"
             :port 4001
             :init (use 'overtone.live)
             })

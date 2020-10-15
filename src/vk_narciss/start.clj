(ns vk-narciss.start
  (:use vk-narciss.core))

(defn is-valid-body
  [response]
  ((complement nil?) (:response response)))

(defn swap-token
  [new-value]
  (swap! default-params
         (fn [curr]
           (assoc curr :access_token new-value))))

(defn ask-token
  []
  (println "Input vk token: ")
  (swap-token (read-line)))

(defn run-narciss-menu
  []
  (println "That's all for now!")
  (System/exit 0))

(defn run-token-menu
  []
  (ask-token)
  (if (is-valid-body (parse-body (get-friends)))
    (run-narciss-menu)
    (do (println "This one is invalid. Try again...")
        (run-token-menu))))

(defn -main
  [& args]
  (run-token-menu))


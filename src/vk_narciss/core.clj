(ns vk-narciss.core
  (:require [org.httpkit.client :as http]
            [clojure.data.json :as json]))

(def base-url "https://api.vk.com/method/")
(def api-version 5.124)
(def default-params (atom {:access_token ""
                           :v api-version}))

(defn get-raw-request
  [method-name opts]
  @(http/get
    (str base-url method-name)
    {:query-params opts}))

(defn get-request
  ([method-name]
   (get-request method-name nil))

  ([method-name, params]
   (let [options (merge params @default-params)]
     (get-raw-request method-name options))))

(def parse-body (comp json/read-json :body))
(def parse-response (comp :response parse-body))
(def parse-items (comp :items parse-response))

(defn get-friends []
  (get-request "friends.get"))

(defn get-my-friends-id []
  (parse-items get-friends))

(defn get-photos
  [id]
  (parse-items (get-request "photos.get" {:owner_id id,
                                          :album_id "profile"})))

(defn get-photos-id
  [id]
  (map :id (get-photos id)))

(defn is-liked
  ([owner-id photo-id]
   (is-liked owner-id photo-id owner-id))

  ([owner-id photo-id user-id]
   (= 1 
      ((comp :liked parse-response) (get-request "likes.isLiked" {:user_id user-id
                                                                  :owner_id owner-id
                                                                  :type "photo"
                                                                  :item_id photo-id})))))

(defn is-narciss
  [id]
  (some #(is-liked id %) (get-photos-id id)))

(defn detect-narcisses
  ([] (detect-narcisses get-my-friends-id))
  ([ids] (filter is-narciss ids)))

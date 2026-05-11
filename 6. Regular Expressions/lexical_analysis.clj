(ns lexical-analysis)

; Read file
; (seq (slurp "work_files/input.txt"))

; Write file
; (spit "work_files/output.txt" "Hi!")

(def my-regex #"(?xi)
      ( -? \d+ [.] \d* (?: e -? \d+)? )   # Group 1: Float
    | ( \d+ )                             # Group 2: Integer
    | ( [a-z] \w* )                       # Group 3: Variable
    | ( // .* )                           # Group 4: Comment
    | ( [=] )                             # Group 5: Assignment
    | ( [+] )                             # Group 6: Addition
    | ( [-] )                             # Group 7: Subtraction
    | ( [*] )                             # Group 8: Multiplication
    | ( [/] )                             # Group 9: Division
    | ( \^ )                              # Group 10: Power
    | ( [(] )                             # Group 11: Opening Parenthesis
    | ( [)] )                             # Group 12: Closing Parenthesis
    | ( \s )                              # Group 13: Spaces
    | ( . )                               # Group 14: Invalid Character (has to be last group)
")

(def categories [nil :float :integer :variable :comment :assignment
                 :addition :subtraction :multiplication :division
                 :power :opening-parenthesis :closing-parenthesis
                 :spaces :invalid-character])

(defn capturing-group-index
  [v]
  (inc (count (take-while nil? (rest v)))))

(defn lexical-analysis
  [file-name]
  (let [matches (re-seq my-regex (slurp file-name))]
    (remove
      #(= :spaces (% 1))
      (map (fn [match]
             [(match 0) (categories (capturing-group-index match))])
           matches))))

(defn separator
  []
  (println (apply str (repeat 56 \=))))

(defn print-table
  [file-name]
  (separator)
  (println (format "%-32sCategory" "Token"))
  (separator)
  (doseq [token (lexical-analysis file-name)]
    (println (format "%-32s%s" (token 0) (symbol (token 1)))))
  (separator))

(print-table "work_files/input.txt")

(ns lexical-analysis)

; Read file
; (seq (slurp "work_files/input.txt"))

; Write file
; (spit "work_files/output.txt" "Hi!")

(def my-regex #"(?xi)
      ( -? \d+ [.] \d* (?: e -? \d+)? )   # Grupo 1: Float
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

(re-seq my-regex (slurp "work_files/input.txt"))

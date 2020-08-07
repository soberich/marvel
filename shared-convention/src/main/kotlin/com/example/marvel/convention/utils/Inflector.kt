@file:Suppress("unused", "KDocMissingDocumentation", "MemberVisibilityCanBePrivate", "SpellCheckingInspection")

package com.example.marvel.convention.utils

import org.intellij.lang.annotations.Language
import java.lang.Character.toLowerCase
import java.lang.Character.toUpperCase
import java.util.*
import java.util.regex.Pattern
import java.util.regex.Pattern.CASE_INSENSITIVE

/**
 * Port from java
 * TODO: Make even more "kotlinish"
 */
object Inflector {

    private val plurals = LinkedList<Rule>()
    private val singulars = LinkedList<Rule>()

    /**
     * The lowercase words that are to be excluded and not processed. This map can be modified by the users via
     * Get the set of words that are not processed by the Inflector. The resulting map is directly modifiable.
     *
     * @return the set of uncountable words
     * [uncountables].
     */
    val uncountables: HashSet<String> = hashSetOf()

    internal class Rule internal constructor(private val expression: String, private val replacement: String) {

        private val expressionPattern = expression.toPattern(CASE_INSENSITIVE)

        /**
         * Apply the rule against the input string, returning the modified string or null if the rule didn't apply (and no
         * modifications were made)
         *
         * @param input the input string
         * @return the modified string if this rule applied, or null if the input was not modified by this rule
         */
        internal fun apply(input: String): String? {
            val matcher = expressionPattern.matcher(input)
            return if (!matcher.find()) null else matcher.replaceAll(replacement)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other != null && other::class.java != this::class.java) return false
            val rule = other as? Rule
            return expression.equals(rule!!.expression, ignoreCase = true)
        }

        override fun hashCode(): Int = expression.hashCode()

        override fun toString(): String = "$expression, $replacement"
    }

    // ------------------------------------------------------------------------------------------------
    // Usage functions
    // ------------------------------------------------------------------------------------------------

    /**
     * Returns the plural form of the word in the string.
     *
     *
     * Examples:
     * ```java
     *     inflector.pluralize("post")               #=> "posts"
     *     inflector.pluralize("octopus")            #=> "octopi"
     *     inflector.pluralize("sheep")              #=> "sheep"
     *     inflector.pluralize("words")              #=> "words"
     *     inflector.pluralize("the blue mailman")   #=> "the blue mailmen"
     *     inflector.pluralize("CamelOctopus")       #=> "CamelOctopi"
     * ```
     *
     * Note that if the [Object.toString] is called on the supplied object, so this method works for non-strings, too.
     *
     * @param word the word that is to be pluralized.
     * @return the pluralized form of the word, or the word itself if it could not be pluralized
     * @see .singularize
     */
    fun pluralize(word: Any): String = grammaticalNumber(word, plurals)

    fun pluralize(word: Any?,
                  count: Int): String? {
        if (word == null) return null
        return if (count == 1 || count == -1) {
            word.toString()
        } else pluralize(word)
    }

    /**
     * Returns the singular form of the word in the string.
     *
     * Examples:
     * ```java
     *     inflector.singularize("posts")             #=> "post"
     *     inflector.singularize("octopi")            #=> "octopus"
     *     inflector.singularize("sheep")             #=> "sheep"
     *     inflector.singularize("words")             #=> "word"
     *     inflector.singularize("the blue mailmen")  #=> "the blue mailman"
     *     inflector.singularize("CamelOctopi")       #=> "CamelOctopus"
     * ```
     *
     * Note that if the [Object.toString] is called on the supplied object, so this method works for non-strings, too.
     *
     * @param word the word that is to be pluralized.
     * @return the pluralized form of the word, or the word itself if it could not be pluralized
     * @see .pluralize
     */
    fun singularize(word: Any?): String? = if (word == null) null else grammaticalNumber(word, singulars)

    private fun grammaticalNumber(word: Any?, rules: List<Rule>): String {
        val wordStr = word.toString().trim { it <= ' ' }
        if (wordStr.isEmpty()) return wordStr
        if (isUncountable(wordStr)) return wordStr
        for (rule in rules) {
            val result = rule.apply(wordStr)
            if (result != null) return result
        }
        return wordStr
    }

    /**
     * Converts strings to lowerCamelCase. This method will also use any extra delimiter characters to identify word boundaries.
     *
     *
     * Examples:
     *
     * <pre>
     * inflector.lowerCamelCase(&quot;active_record&quot;)       #=&gt; &quot;activeRecord&quot;
     * inflector.lowerCamelCase(&quot;first_name&quot;)          #=&gt; &quot;firstName&quot;
     * inflector.lowerCamelCase(&quot;name&quot;)                #=&gt; &quot;name&quot;
     * inflector.lowerCamelCase(&quot;the-first_name&quot;,'-')  #=&gt; &quot;theFirstName&quot;
    </pre> *
     *
     * @param lowerCaseAndUnderscoredWord the word that is to be converted to camel case
     * @param delimiterChars              optional characters that are used to delimit word boundaries
     * @return the lower camel case version of the word
     * @see .underscore
     * @see .camelCase
     * @see .upperCamelCase
     */
    fun lowerCamelCase(lowerCaseAndUnderscoredWord: String, vararg delimiterChars: Char): String? =
        camelCase(lowerCaseAndUnderscoredWord, false, *delimiterChars)

    /**
     * Converts strings to UpperCamelCase. This method will also use any extra delimiter characters to identify word boundaries.
     *
     * Examples:
     * ```
     *     inflector.upperCamelCase("active_record")       #=> "SctiveRecord"
     *     inflector.upperCamelCase("first_name")          #=> "FirstName"
     *     inflector.upperCamelCase("name")                #=> "Name"
     *     inflector.lowerCamelCase("the-first_name",'-')  #=> "TheFirstName"
     * ```
     *
     * @param lowerCaseAndUnderscoredWord the word that is to be converted to camel case
     * @param delimiterChars              optional characters that are used to delimit word boundaries
     * @return the upper camel case version of the word
     * @see .underscore
     * @see .camelCase
     * @see .lowerCamelCase
     */
    fun upperCamelCase(lowerCaseAndUnderscoredWord: String, vararg delimiterChars: Char): String? =
        camelCase(lowerCaseAndUnderscoredWord, true, *delimiterChars)

    /**
     * By default, this method converts strings to UpperCamelCase. If the `uppercaseFirstLetter` argument to false,
     * then this method produces lowerCamelCase. This method will also use any extra delimiter characters to identify word
     * boundaries.
     *
     * Examples:
     * ```java
     *     inflector.camelCase("active_record",false)    #=> "activeRecord"
     *     inflector.camelCase("active_record",true)     #=> "ActiveRecord"
     *     inflector.camelCase("first_name",false)       #=> "firstName"
     *     inflector.camelCase("first_name",true)        #=> "FirstName"
     *     inflector.camelCase("name",false)             #=> "name"
     *     inflector.camelCase("name",true)              #=> "Name"
     * ```
     * @param lowerCaseAndUnderscoredWord the word that is to be converted to camel case
     * @param uppercaseFirstLetter        true if the first character is to be uppercased, or false if the first character is to be
     * lowercased
     * @param delimiterChars              optional characters that are used to delimit word boundaries
     * @return the camel case version of the word
     * @see .underscore
     * @see .upperCamelCase
     * @see .lowerCamelCase
     */
    fun camelCase(lowerCaseAndUnderscoredWord: String?, uppercaseFirstLetter: Boolean, vararg delimiterChars: Char): String? {
        var lowerCaseAndUnderscoredWord: String? = lowerCaseAndUnderscoredWord ?: return null
        lowerCaseAndUnderscoredWord = (lowerCaseAndUnderscoredWord ?: return null).trim { it <= ' ' }
        if (lowerCaseAndUnderscoredWord.isEmpty()) return ""
        if (uppercaseFirstLetter) {
            var result: String = lowerCaseAndUnderscoredWord
            // Replace any extra delimiters with underscores (before the underscores are converted in the next step)...
            for (delimiterChar in delimiterChars) {
                result = result.replace(delimiterChar, '_')
            }

            // Change the case at the beginning at after each underscore ...
            return replaceAllWithUppercase(result, "(^|_)(.)", 2)

        }
        return if (lowerCaseAndUnderscoredWord.length < 2) lowerCaseAndUnderscoredWord else "" + toLowerCase(lowerCaseAndUnderscoredWord[0]) + camelCase(lowerCaseAndUnderscoredWord, true, *delimiterChars)?.substring(1).orEmpty()

    }

    /**
     * Makes an underscored form from the expression in the string (the reverse of the [ camelCase][.camelCase] method. Also changes any characters that match the supplied delimiters into underscore.
     *
     *
     * Examples:
     * ```java
     *     inflector.underscore("activeRecord")     #=> "active_record"
     *     inflector.underscore("ActiveRecord")     #=> "active_record"
     *     inflector.underscore("firstName")        #=> "first_name"
     *     inflector.underscore("FirstName")        #=> "first_name"
     *     inflector.underscore("name")             #=> "name"
     *     inflector.underscore("The.firstName")    #=> "the_first_name"
     * ```
     *
     * @param camelCaseWord  the camel-cased word that is to be converted;
     * @param delimiterChars optional characters that are used to delimit word boundaries (beyond capitalization)
     * @return a lower-cased version of the input, with separate words delimited by the underscore character.
     */
    fun underscore(camelCaseWord: String?, vararg delimiterChars: Char): String? {
        if (camelCaseWord == null) return null
        var result = camelCaseWord.trim { it <= ' ' }
        if (result.isEmpty()) return ""
        result = result.replace("([A-Z]+)([A-Z][a-z])".toRegex(), "$1_$2")
        result = result.replace("([a-z\\d])([A-Z])".toRegex(), "$1_$2")
        result = result.replace('-', '_')
        for (delimiterChar in delimiterChars) {
            result = result.replace(delimiterChar, '_')
        }
        return result.toLowerCase()
    }

    /**
     * Returns a copy of the input with the first character converted to uppercase and the remainder to lowercase.
     *
     * @param words the word to be capitalized
     * @return the string with the first character capitalized and the remaining characters lowercased
     */
    fun capitalize(words: String?): String? = when (val result = words?.trim { it <= ' ' }) {
        null -> null
        ""   -> ""
        else -> if (result.length == 1) result.toUpperCase() else result.toLowerCase().capitalize()
    }

    /**
     * Capitalizes the first word and turns underscores into spaces and strips trailing "id" and any supplied removable tokens.
     * Like [.titleCase], this is meant for creating pretty output.
     *
     *
     * Examples:
     * ```java
     *    inflector.humanize("employee_salary")       #=> "Employee salary"
     *    inflector.humanize("authorid ")             #=> "Author"
     * ```
     *
     * @param lowerCaseAndUnderscoredWords the input to be humanized
     * @param removableTokens              optional array of tokens that are to be removed
     * @return the humanized string
     * @see .titleCase
     */
    fun humanize(lowerCaseAndUnderscoredWords: String?, vararg removableTokens: String): String? {
        if (lowerCaseAndUnderscoredWords == null) return null
        var result = lowerCaseAndUnderscoredWords.trim { it <= ' ' }
        if (result.isEmpty()) return ""
        // Remove a trailing "id" token
        result = result.replace("id$".toRegex(), "")
        // Remove all of the tokens that should be removed
        for (removableToken in removableTokens) {
            result = result.replace(removableToken.toRegex(), "")
        }
        result = result.replace("_+".toRegex(), " ") // replace all adjacent underscores with a single space
        return capitalize(result)
    }

    /**
     * Capitalizes all the words and replaces some characters in the string to create a nicer looking title. Underscores are
     * changed to spaces, a trailing "id" is removed, and any of the supplied tokens are removed. Like
     * [.humanize], this is meant for creating pretty output.
     *
     *
     * Examples:
     * ```java
     *     inflector.titleCase("man from the boondocks")       #=> "Man From The Boondocks"
     *     inflector.titleCase("x-men: the last stand")        #=> "X Men: The Last Stand"
     * ```
     *
     * @param words           the input to be turned into title case
     * @param removableTokens optional array of tokens that are to be removed
     * @return the title-case version of the supplied words
     */
    fun titleCase(words: String, vararg removableTokens: String): String {
        var result = humanize(words, *removableTokens)
        result = replaceAllWithUppercase(result, "\\b([a-z])", 1) // change first char of each word to uppercase
        return result
    }

    /**
     * Turns a non-negative number into an ordinal string used to denote the position in an ordered sequence, such as 1st, 2nd,
     * 3rd, 4th.
     *
     * @param i the non-negative number
     * @return the string with the number and ordinal suffix
     */
    fun ordinalize(i: Int): String = "$i" + if (i % 100 in 11..13) "th" else when (i % 10) {
        1    -> "st"
        2    -> "nd"
        3    -> "rd"
        else -> "th"
    }

    // ------------------------------------------------------------------------------------------------
    // Management methods
    // ------------------------------------------------------------------------------------------------

    /**
     * Determine whether the supplied word is considered uncountable by the [pluralize][.pluralize] and
     * [singularize][.singularize] methods.
     * @return true if the plural and singular forms of the word are the same
     */
    fun isUncountable(word: String?): Boolean =
        word != null && uncountables.contains(word.trim { it <= ' ' }.toLowerCase())

    fun addPluralize(@Language("RegExp") rule: String, replacement: String): Unit = plurals.addFirst(Rule(rule, replacement))

    fun addSingularize(@Language("RegExp") rule: String, replacement: String): Unit = singulars.addFirst(Rule(rule, replacement))

    @SuppressWarnings("squid:S125")
    fun addIrregular(singular: String, plural: String) {
        //CheckArg.isNotEmpty(singular, "singular rule");
        //CheckArg.isNotEmpty(plural, "plural rule");
        val singularRemainder = if (singular.length > 1) singular.substring(1) else ""
        val pluralRemainder = if (plural.length > 1) plural.substring(1) else ""
        addPluralize("(${singular[0]})$singularRemainder$", "$1$pluralRemainder")
        addSingularize("(${plural[0]})$pluralRemainder$", "$1$singularRemainder")
    }

    fun addUncountable(vararg words: String) {
        if (words.isEmpty()) return
        for (word in words) {
            uncountables.add(word.trim { it <= ' ' }.toLowerCase())
        }
    }

    /**
     * Completely remove all rules within this inflector.
     */
    fun clear() {
        uncountables.clear()
        plurals.clear()
        singulars.clear()
    }

    init {
        val inflect = this
        inflect.addPluralize("$", "s")
        inflect.addPluralize("s$", "s")
        inflect.addPluralize("(ax|test)is$", "$1es")
        inflect.addPluralize("(octop|vir)us$", "$1i")
        inflect.addPluralize("(octop|vir)i$", "$1i") // already plural
        inflect.addPluralize("(alias|status)$", "$1es")
        inflect.addPluralize("(bu)s$", "$1ses")
        inflect.addPluralize("(buffal|tomat)o$", "$1oes")
        inflect.addPluralize("([ti])um$", "$1a")
        inflect.addPluralize("([ti])a$", "$1a") // already plural
        inflect.addPluralize("sis$", "ses")
        inflect.addPluralize("(?:([^f])fe|([lr])f)$", "$1$2ves")
        inflect.addPluralize("(hive)$", "$1s")
        inflect.addPluralize("([^aeiouy]|qu)y$", "$1ies")
        inflect.addPluralize("(x|ch|ss|sh)$", "$1es")
        inflect.addPluralize("(matr|vert|ind)ix|ex$", "$1ices")
        inflect.addPluralize("([m|l])ouse$", "$1ice")
        inflect.addPluralize("([m|l])ice$", "$1ice")
        inflect.addPluralize("^(ox)$", "$1en")
        inflect.addPluralize("(quiz)$", "$1zes")
        // Need to check for the following words that are already pluralized:
        inflect.addPluralize("(people|men|children|sexes|moves|stadiums)$", "$1") // irregulars
        inflect.addPluralize("(oxen|octopi|viri|aliases|quizzes)$", "$1") // special rules

        inflect.addSingularize("s$", "")
        inflect.addSingularize("(s|si|u)s$", "$1s") // '-us' and '-ss' are already singular
        inflect.addSingularize("(n)ews$", "$1ews")
        inflect.addSingularize("([ti])a$", "$1um")
        inflect.addSingularize("((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$", "$1$2sis")
        inflect.addSingularize("(^analy)ses$", "$1sis")
        inflect.addSingularize("(^analy)sis$", "$1sis") // already singular, but ends in 's'
        inflect.addSingularize("([^f])ves$", "$1fe")
        inflect.addSingularize("(hive)s$", "$1")
        inflect.addSingularize("(tive)s$", "$1")
        inflect.addSingularize("([lr])ves$", "$1f")
        inflect.addSingularize("([^aeiouy]|qu)ies$", "$1y")
        inflect.addSingularize("(s)eries$", "$1eries")
        inflect.addSingularize("(m)ovies$", "$1ovie")
        inflect.addSingularize("(x|ch|ss|sh)es$", "$1")
        inflect.addSingularize("([m|l])ice$", "$1ouse")
        inflect.addSingularize("(bus)es$", "$1")
        inflect.addSingularize("(o)es$", "$1")
        inflect.addSingularize("(shoe)s$", "$1")
        inflect.addSingularize("(cris|ax|test)is$", "$1is") // already singular, but ends in 's'
        inflect.addSingularize("(cris|ax|test)es$", "$1is")
        inflect.addSingularize("(octop|vir)i$", "$1us")
        inflect.addSingularize("(octop|vir)us$", "$1us") // already singular, but ends in 's'
        inflect.addSingularize("(alias|status)es$", "$1")
        inflect.addSingularize("(alias|status)$", "$1") // already singular, but ends in 's'
        inflect.addSingularize("^(ox)en", "$1")
        inflect.addSingularize("(vert|ind)ices$", "$1ex")
        inflect.addSingularize("(matr)ices$", "$1ix")
        inflect.addSingularize("(quiz)zes$", "$1")

        inflect.addIrregular("person", "people")
        inflect.addIrregular("man", "men")
        inflect.addIrregular("child", "children")
        inflect.addIrregular("sex", "sexes")
        inflect.addIrregular("move", "moves")
        inflect.addIrregular("stadium", "stadiums")

        inflect.addUncountable("equipment", "information", "rice", "money", "species", "series", "fish", "sheep")
    }

    /**
     * Utility method to replace all occurrences given by the specific backreference with its uppercased form, and remove all
     * other backreferences.
     *
     * The Java [regular expression processing][Pattern] does not use the preprocessing directives `\l`,
     * `&#92;u`, `\L`, and `\U`. If so, such directives could be used in the replacement string
     * to uppercase or lowercase the backreferences. For example, `\L1` would lowercase the first backreference, and
     * `&#92;u3` would uppercase the 3rd backreference.
     * @return the input string with the appropriate characters converted to upper-case
     */
    private fun replaceAllWithUppercase(input: String?, regex: String, groupNumberToUppercase: Int): String {
        val underscoreAndDotPattern = regex.toPattern()
        val matcher = underscoreAndDotPattern.matcher(input!!)
        val sb = StringBuffer()
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(groupNumberToUppercase).toUpperCase())
        }
        matcher.appendTail(sb)
        return sb.toString()
    }
}

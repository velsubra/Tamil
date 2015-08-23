package tamil.lang.api.regex;

import my.interest.lang.tamil.internal.api.IPropertyFinder;
import tamil.lang.exception.service.ServiceException;
import tamil.util.regx.TamilPattern;

import java.util.Set;


/**
 * <p>
 * The Tamil regular expression compiler. The expression can contain what is known as Tamil expression which is of the form ${..}.
 * Please see {@link tamil.lang.api.ezhuththu.EzhuththuDescription} to know all the patterns for representing a character from known finite sets.
 * </p>
 * <p>
 * The below table indicates other set of patterns that can be used in regular expressions.
 * <table border='1'></tr><th align='center'>S.No</th><th align='center'>Usage in Regular Expression</th><th align='center'>Description of the expression</th></tr><tr><td>1</td><td>${!எழுத்து}</td><td>Characters outside Tamil block</td></tr><tr><td>2</td><td>${!எழுத்துவடிவம்}</td><td>Characters outside Tamil block</td></tr><tr><td>3</td><td>${எழுத்துவடிவம்}</td><td>Represent any code point. It may not be a Tamil character.</td></tr><tr><td>4</td><td>${கருவிளங்கனி}</td><td>வாய்ப்பாடு 'கருவிளங்கனி' -ஐக்குறிக்கிறது.</td></tr><tr><td>5</td><td>${கருவிளங்காய்}</td><td>வாய்ப்பாடு 'கருவிளங்காய்' -ஐக்குறிக்கிறது.</td></tr><tr><td>6</td><td>${கருவிளம்}</td><td>வாய்ப்பாடு 'கருவிளம்' -ஐக்குறிக்கிறது.</td></tr><tr><td>7</td><td>${கூவிளங்கனி}</td><td>வாய்ப்பாடு 'கூவிளங்கனி' -ஐக்குறிக்கிறது.</td></tr><tr><td>8</td><td>${கூவிளங்காய்}</td><td>வாய்ப்பாடு 'கூவிளங்காய்' -ஐக்குறிக்கிறது.</td></tr><tr><td>9</td><td>${கூவிளம்}</td><td>வாய்ப்பாடு 'கூவிளம்' -ஐக்குறிக்கிறது.</td></tr><tr><td>10</td><td>${தேமா}</td><td>வாய்ப்பாடு 'தேமா' -ஐக்குறிக்கிறது.</td></tr><tr><td>11</td><td>${தேமாங்கனி}</td><td>வாய்ப்பாடு 'தேமாங்கனி' -ஐக்குறிக்கிறது.</td></tr><tr><td>12</td><td>${தேமாங்காய்}</td><td>வாய்ப்பாடு 'தேமாங்காய்' -ஐக்குறிக்கிறது.</td></tr><tr><td>13</td><td>${நிரை}</td><td>வாய்ப்பாடு 'நிரை' -ஐக்குறிக்கிறது.</td></tr><tr><td>14</td><td>${நிரை}</td><td>வாய்ப்பாடு 'நிரை' -ஐக்குறிக்கிறது.</td></tr><tr><td>15</td><td>${நிரைபு}</td><td>வாய்ப்பாடு 'நிரைபு' -ஐக்குறிக்கிறது.</td></tr><tr><td>16</td><td>${நிரைபு}</td><td>வாய்ப்பாடு 'நிரைபு' -ஐக்குறிக்கிறது.</td></tr><tr><td>17</td><td>${நேர்}</td><td>வாய்ப்பாடு 'நேர்' -ஐக்குறிக்கிறது.</td></tr><tr><td>18</td><td>${நேர்}</td><td>வாய்ப்பாடு 'நேர்' -ஐக்குறிக்கிறது.</td></tr><tr><td>19</td><td>${நேர்பு}</td><td>வாய்ப்பாடு 'நேர்பு' -ஐக்குறிக்கிறது.</td></tr><tr><td>20</td><td>${நேர்பு}</td><td>வாய்ப்பாடு 'நேர்பு' -ஐக்குறிக்கிறது.</td></tr><tr><td>21</td><td>${புளிமா}</td><td>வாய்ப்பாடு 'புளிமா' -ஐக்குறிக்கிறது.</td></tr><tr><td>22</td><td>${புளிமாங்கனி}</td><td>வாய்ப்பாடு 'புளிமாங்கனி' -ஐக்குறிக்கிறது.</td></tr><tr><td>23</td><td>${புளிமாங்காய்}</td><td>வாய்ப்பாடு 'புளிமாங்காய்' -ஐக்குறிக்கிறது.</td></tr><tr><td>24</td><td>${மொழி}</td><td>Pattern for Tamil word. A possible sequence that could be a Tamil Word.</td></tr></table>
 * </p>
 * <p>
 * The following patterns have special meaning
 * <p/>
 * <table border='1'></tr><th align='center'>S.No</th><th align='center'>Usage in Regular Expression</th><th align='center'>Description of the expression</th></tr>
 * <tr><td>1</td><td>${(..}</td> <td> Pattern to be found only at the start of a word.</td> </tr>
 * <tr><td>2</td><td>${..)}</td> <td> Pattern to be found only at the end of a word.</td> </tr>
 * <tr><td>3</td><td>${(..)}</td> <td> Pattern to be found as whole word only.</td> </tr>
 * <tr><td>4</td><td>${அசை[சொல்]}</td> <td> Pattern to match the same list of அசை as in the given சொல்</td> </tr>
 * <tr><td>5</td><td>${மாத்திரை[சொல்]}</td> <td> Pattern to match the same list of மாத்திரை for each character  as in  the given சொல்</td> </tr>
 * <tr><td>6</td><td>${வகை[சொல்]}</td> <td> Pattern to match the same list of வகை (உயிர், உயிர்மெய்ம் மெய், ஆய்தம்) of each character  as in the given சொல்</td> </tr>
 *  <tr><td>7</td><td>${மெய்வகை[சொல்]}</td> <td> Pattern to match the same list of மெய்வகை (வலி,மெலி,இடை) of each character  as in the given சொல்</td> </tr>
 * </table>
 * <p/>
 * </p>
 * <p/>
 * <p>
 * <p/>
 * It is capable of compiling a standard expression containing tamil expression and produce standard {@link java.util.regex.Matcher}  against a character sequence.
 * <p/>
 * <p/>
 * </p>
 *
 * @author velsubra
 * @see tamil.lang.TamilFactory#getRegXCompiler()
 */
public interface TamilRXCompiler {

    /**
     * Compiles a regular expression.
     *
     * @param rx the Tamil regular expression
     * @return the compiled pattern. One can get Java matcher by calling  {@link tamil.util.regx.TamilPattern#matcher(CharSequence)}
     * @throws ServiceException
     */
    public TamilPattern compile(String rx) throws ServiceException;

    /**
     * Compiles a regular expression that can contain aliases.
     *
     * @param rx      the Tamil regular expression
     * @param aliases the aliases definition. Defined aliases could be used in the regular expression.
     *                Example: குற்றியலுகரம் can be defined as  குற்றியலுகரம்=${வலியுகரவரிசை}  and ${குற்றியலுகரம்} can be used in the Tamil regular expression to mean any of the குற்றியலுகரம் character.
     *                <b><Note:/b>வலியுகரவரிசை can be calculated by
     *                {@link tamil.lang.api.ezhuththu.TamilCharacterSetCalculator#find(String)} method as வலி and  உகரவரிசை  are available as already known character sets returned by {@link tamil.lang.api.ezhuththu.TamilCharacterSetCalculator#getEzhuththuDescriptions()}  }
     *                <p/>
     *                <p>
     *                To define   குற்றியலுகரம்=${வலியுகரவரிசை},  IPropertyFinder.findProperty("குற்றியலுகரம்") should return the string literal  ${வலியுகரவரிசை}
     *                </p>
     * @return the compiled pattern. One can get Java matcher by calling  {@link tamil.util.regx.TamilPattern#matcher(CharSequence)}
     * @throws ServiceException
     */
    public TamilPattern compile(String rx, IPropertyFinder aliases) throws ServiceException;


    /**
     * Returns the set of rx patterns of the for ${@link RxDescription#getName()} that can be used in a Tamil Regular Expression
     *
     * @return the set of descriptions
     * @throws ServiceException
     */
    public Set<? extends RxDescription> getRegXDescriptions() throws ServiceException;
}

package tamil.lang.api.regex;

import tamil.util.IPropertyFinder;
import tamil.lang.exception.service.ServiceException;
import tamil.util.regex.TamilPattern;

import java.util.Set;


/**
 * <p>
 * The Tamil regular expression compiler. The expression can contain what is known as Tamil expression which is of the form ${..}.
 * Please see {@link tamil.lang.api.ezhuththu.EzhuththuSetDescription} to know all the patterns for representing a character from known finite sets.
 * </p>
 * <p>
 * The below table indicates other set of patterns that can be used in regular expressions.
 *<table border='1'></tr><th align='center'>S.No</th><th align='center'>Usage in Regular Expression</th><th align='center'>Description of the expression</th></tr><tr><td>1</td><td>${!எழுத்து}</td><td>Code points outside Tamil block. Note: !எழுத்து and !எழுத்துவடிவம் mean the same thing. However, எழுத்து and எழுத்துவடிவம் mean different things. </td></tr><tr><td>2</td><td>${!எழுத்துவடிவம்}</td><td>Code points outside Tamil block. Note: !எழுத்து and !எழுத்துவடிவம் mean the same thing. However, எழுத்து and எழுத்துவடிவம் mean different things. </td></tr><tr><td>3</td><td>${அசை}</td><td>ஏதேனும் ஒர் அசையைக்குறிக்கிறது.</td></tr><tr><td>4</td><td>${அரைமாத்திரை}</td><td>அரைமாத்திரைகொண்டு ஒலிக்கும் பாங்கு</td></tr><tr><td>5</td><td>${இடைவெளி}</td><td>One or more white spaces.  space or new line etc..</td></tr><tr><td>6</td><td>${இயற்சீர்வெண்டளை}</td><td>இயற்சீர்வெண்டளை</td></tr><tr><td>7</td><td>${இருமாத்திரை}</td><td>இருமாத்திரைகொண்டு ஒலிக்கும் பாங்கு</td></tr><tr><td>8</td><td>${ஈரசைச்சீர்}</td><td>ஏதேனும் ஒர் ஈரசைச்சீரைக்குறிக்கிறது. </td></tr><tr><td>9</td><td>${எழுத்துவடிவம்}</td><td>Represent any Tamil code point. Note: It may not be a full Tamil character. </td></tr><tr><td>10</td><td>${ஒருமாத்திரை}</td><td>ஒருமாத்திரைகொண்டு ஒலிக்கும் பாங்கு</td></tr><tr><td>11</td><td>${கனிச்சீர்}</td><td>எதேனுமொரு கனிச்சீரைக்குறிக்கிறது. </td></tr><tr><td>12</td><td>${கருவிளங்கனி}</td><td>வாய்ப்பாடு 'கருவிளங்கனி' -ஐக்குறிக்கிறது.</td></tr><tr><td>13</td><td>${கருவிளங்காய்}</td><td>வாய்ப்பாடு 'கருவிளங்காய்' -ஐக்குறிக்கிறது.</td></tr><tr><td>14</td><td>${கருவிளம்}</td><td>வாய்ப்பாடு 'கருவிளம்' -ஐக்குறிக்கிறது.</td></tr><tr><td>15</td><td>${காசு}</td><td>வாய்ப்பாடு 'காசு' -ஐக்குறிக்கிறது.</td></tr><tr><td>16</td><td>${காய்ச்சீர்}</td><td>எதேனுமொரு காய்ச்சீரைக்குறிக்கிறது. </td></tr><tr><td>17</td><td>${குறளின் சீரமைப்பு}</td><td>சீர்களின் அமைப்புவழியாக திருக்குறளின் பாங்கு</td></tr><tr><td>18</td><td>${குறளின் தளையமைப்பு}</td><td>தளைகளின் அமைப்புவழியாக திருக்குறளின் பாங்கு</td></tr><tr><td>19</td><td>${குறள்}</td><td>குறளுக்கான பாங்கு. சீர்களின் அமைப்பையும்  தளைகளின் அமைப்பையும் உள்ளடக்குகிறது.    </td></tr><tr><td>20</td><td>${குற்றுக்குறில்}</td><td>குஅ to be treated as a single character  க</td></tr><tr><td>21</td><td>${குற்றுநெடில்}</td><td>குஔ to be treated as a single character  கௌ</td></tr><tr><td>22</td><td>${கூவிளங்கனி}</td><td>வாய்ப்பாடு 'கூவிளங்கனி' -ஐக்குறிக்கிறது.</td></tr><tr><td>23</td><td>${கூவிளங்காய்}</td><td>வாய்ப்பாடு 'கூவிளங்காய்' -ஐக்குறிக்கிறது.</td></tr><tr><td>24</td><td>${கூவிளம்}</td><td>வாய்ப்பாடு 'கூவிளம்' -ஐக்குறிக்கிறது.</td></tr><tr><td>25</td><td>${கொக்கி}</td><td>Represent Tamil Glyph</td></tr><tr><td>26</td><td>${தேமா}</td><td>வாய்ப்பாடு 'தேமா' -ஐக்குறிக்கிறது.</td></tr><tr><td>27</td><td>${தேமாங்கனி}</td><td>வாய்ப்பாடு 'தேமாங்கனி' -ஐக்குறிக்கிறது.</td></tr><tr><td>28</td><td>${தேமாங்காய்}</td><td>வாய்ப்பாடு 'தேமாங்காய்' -ஐக்குறிக்கிறது.</td></tr><tr><td>29</td><td>${நாள்}</td><td>வாய்ப்பாடு 'நாள்' -ஐக்குறிக்கிறது.</td></tr><tr><td>30</td><td>${நிரை}</td><td>வாய்ப்பாடு 'நிரை' -ஐக்குறிக்கிறது.</td></tr><tr><td>31</td><td>${நிரைபு}</td><td>வாய்ப்பாடு 'நிரைபு' -ஐக்குறிக்கிறது.</td></tr><tr><td>32</td><td>${நேர்}</td><td>வாய்ப்பாடு 'நேர்' -ஐக்குறிக்கிறது.</td></tr><tr><td>33</td><td>${நேர்பு}</td><td>வாய்ப்பாடு 'நேர்பு' -ஐக்குறிக்கிறது.</td></tr><tr><td>34</td><td>${பிறப்பு}</td><td>வாய்ப்பாடு 'பிறப்பு' -ஐக்குறிக்கிறது.</td></tr><tr><td>35</td><td>${புளிமா}</td><td>வாய்ப்பாடு 'புளிமா' -ஐக்குறிக்கிறது.</td></tr><tr><td>36</td><td>${புளிமாங்கனி}</td><td>வாய்ப்பாடு 'புளிமாங்கனி' -ஐக்குறிக்கிறது.</td></tr><tr><td>37</td><td>${புளிமாங்காய்}</td><td>வாய்ப்பாடு 'புளிமாங்காய்' -ஐக்குறிக்கிறது.</td></tr><tr><td>38</td><td>${மலர்}</td><td>வாய்ப்பாடு 'மலர்' -ஐக்குறிக்கிறது.</td></tr><tr><td>39</td><td>${மாச்சீர்}</td><td>எதேனுமொரு மாச்சீரைக்குறிக்கிறது. </td></tr><tr><td>40</td><td>${மாமுன் நிரை}</td><td>இயற்சீர்வெண்டளையின் ஒருவகை.</td></tr><tr><td>41</td><td>${மூவசைச்சீர்}</td><td>எதேனுமொரு மூவசைச்சீரைக்குறிக்கிறது. </td></tr><tr><td>42</td><td>${மொழி}</td><td>Pattern for a Tamil word. A possible sequence that could be a Tamil Word.</td></tr><tr><td>43</td><td>${விளச்சீர்}</td><td>எதேனுமொரு விளச்சீரைக்குறிக்கிறது. </td></tr><tr><td>44</td><td>${விளம்முன் நேர்}</td><td>இயற்சீர்வெண்டளையின் ஒருவகை.</td></tr><tr><td>45</td><td>${வெண்சீர்வெண்டளை}</td><td>வெண்சீர்வெண்டளை (காய்முன் நேர்)</td></tr><tr><td>46</td><td>${வெண்டளை}</td><td>இயற்சீர்வெண்டளை அல்லது வெண்சீர்வெண்டளை</td></tr><tr><td>47</td><td>${வெண்பாவின் இறுதிச்சீர்}</td><td>நாள் ,  மலர் ,  காசு ,  பிறப்பு  ஆகியவற்றில் ஏதேனுமொன்றின் வாய்ப்பாட்டைக்குறிக்கிறது.  </td></tr></table>
 * </p>
 * <p>
 * The following patterns have special meaning
 * <p/>
 * <table border='1'></tr><th align='center'>S.No</th><th align='center'>Usage in Regular Expression</th><th align='center'>Description of the expression</th></tr>
 * <tr><td>1</td><td>${</b>(</b>..}</td> <td> Pattern to be found only at the start of a word.</td> </tr>
 * <tr><td>2</td><td>${..</b>)</b>}</td> <td> Pattern to be found only at the end of a word.</td> </tr>
 * <tr><td>3</td><td>${</b>(</b>..</b>)</b>}</td> <td> Pattern to be found as whole word only. ${(மொழி)}          matches syntactically right whole words.</td> </tr>
 * <tr><td>4</td><td>${<b>அசை</b>[சொல்]}</td> <td> Pattern to match the same list of அசை as in the given சொல். ${அசை[செந்தமிழ்] matches கூவிளம் as  both செந்தமிழ் and கூவிளம்   have same asai patterns.</td> </tr>
 * <tr><td>5</td><td>${<b>மாத்திரை</b>[சொல்]}</td> <td> Pattern to match the same list of மாத்திரை for each character  as in  the given சொல். ${மாத்திரை[தென்னை]} matches with கண்ணா</td> </tr>
 * <tr><td>6</td><td>${<b>வகை</b>[சொல்]}</td> <td> Pattern to match the same list of வகை (உயிர், உயிர்மெய் மெய், ஆய்தம்) of each character  as in the given சொல். ${வகை[தமிழ்]} matches with பாகம்</td> </tr>
 *  <tr><td>7</td><td>${<b>மெய்வகை</b>[சொல்]}</td> <td> Pattern to match the same list of மெய்வகை (வலி,மெலி,இடை) of each character  as in the given சொல். ${மெய்வகை[அங்கு] matches with அந்தி</td> </tr>
 *  <tr><td>8</td><td>${[தமிழ்]}</td> <td> Pattern to match the literal text தமிழ்</td> </tr>
 *  <tr><td>9</td><td>${<b>தளை</b>[ex1 முன் ex2]}</td> <td> Pattern to match தளை. ${தளை[(மாச்சீர்) முன் நிரை]}  என்பது இயற்சீர்வெண்டளையை குறிக்கும்.
 *  குறிப்பு: இரண்டாஞ்சீரின் தன்மையை ஆராய்ந்துதான் தளை காணப்பட்டாலும், தளைக்கான பாங்கு முதற்சீரையும்(அதாவது  ex1) அதற்குப்பின்வரும் இடைவெளியையும்மட்டுமே   குறிக்கும், இரண்டாஞ்சீரை பாங்குடன் இணைக்காது.
 *  This expression will only match ex1 and the whitespaces following it.
 *  The expression ex2 is not part of the matching al though the correctness of the binding(தளை) is tested along with expression ex2.
 *  This is to facilitate the sub-sequent search that could involve the the expression ex2, the second cir.  </td> </tr>
 * </table>
 * <p/>
 * </p>
 * <p/>
 * <p>
 * <p/>
 * It is capable of compiling a  expression containing tamil expression and produce standard {@link java.util.regex.Matcher}  against a character sequence.
 * <p/>
 * <p/>
 * </p>
 *
 * @author velsubra
 * @see tamil.lang.TamilFactory#getRegEXCompiler()
 */
public interface TamilRXCompiler {

    /**
     * Compiles a regular expression.
     *
     * @param rx the Tamil regular expression
     * @return the compiled pattern. One can get Java matcher by calling  {@link tamil.util.regex.TamilPattern#matcher(CharSequence)}
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
     *                {@link tamil.lang.api.ezhuththu.TamilCharacterSetCalculator#find(String)} method as வலி and  உகரவரிசை  are available as already known character sets returned by {@link tamil.lang.api.ezhuththu.TamilCharacterSetCalculator#getEzhuththuSetDescriptions()}  }
     *                <p/>
     *                <p>
     *                To define   குற்றியலுகரம்=${வலியுகரவரிசை},  IPropertyFinder.findProperty("குற்றியலுகரம்") should return the string literal  ${வலியுகரவரிசை}
     *                </p>
     * @param  features the features used while compiling the regular expression.
     * @return the compiled pattern. One can get Java matcher by calling  {@link tamil.util.regex.TamilPattern#matcher(CharSequence)}
     * @throws ServiceException
     */
    public TamilPattern compile(String rx, IPropertyFinder aliases, RXFeature ... features) throws ServiceException;


    /**
     * Returns the set of rx patterns of the for ${@link RxDescription#getName()} that can be used in a Tamil Regular Expression
     *
     * @return the set of descriptions
     * @throws ServiceException
     */
    public Set<? extends RxDescription> getRegXDescriptions() throws ServiceException;
}

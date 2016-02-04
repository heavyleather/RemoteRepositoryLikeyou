package misc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

public class DateTypeConverter extends StrutsTypeConverter {
	private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if(values!=null && values.length!=0) {
			if(values[0]!=null && values[0].length()!=0) {
				try {
					return sFormat.parse(values[0]);
				} catch (ParseException e) {
					e.printStackTrace();
					throw new TypeConversionException("Invalid format.");
				}
			}
		}
		return null;
	}
	@Override
	public String convertToString(Map context, Object obj) {
		if(obj!=null && (obj instanceof java.util.Date)) {
			java.util.Date temp = (java.util.Date) obj;
			return sFormat.format(temp);
		}
		return null;
	}

}

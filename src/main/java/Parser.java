import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Parser {
    JSONParser parser = new JSONParser();
    static String json =  Paths.get("").toAbsolutePath().toString()+"\\src\\test\\resources\\elements\\login.json";
    static String config =  Paths.get("").toAbsolutePath().toString()+"\\src\\test\\config.json";
    public void parse() throws  IOException, ParseException
    {

        JSONObject ba = (JSONObject) parser.parse(new FileReader(json));
        JSONArray  a= (JSONArray) ba.get("pages");

        for (Object o : a) {
            JSONObject page = (JSONObject) o;

            JSONObject pageInfo = (JSONObject) page.get("pageInfo");
            String pagename=(String)pageInfo.get("pageName");
            System.out.println(pagename);

            String logo = (String) pageInfo.get("logo");
            System.out.println(logo);
            String parent = (String) pageInfo.get("parent");
            System.out.println(parent);

            JSONArray cars = (JSONArray) page.get("elements");

            for (Object c : cars) {
                System.out.println(c + "");
            }
        }
    }

    public Boolean isPageExist(String mypage)
    {
        JSONObject object = null;
        try
        {
            object = (JSONObject) parser.parse(new FileReader(json));
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }
        JSONArray  array= (JSONArray) object.get("pages");

    for (Object o : array)
    {
        JSONObject page = (JSONObject) o;

        JSONObject pageInfo = (JSONObject) page.get("pageInfo");
        String pagename=(String)pageInfo.get("pageName");

        if(pagename.equalsIgnoreCase(mypage))
        {
            System.out.println(pagename+" sayfası bulundu");
            return true;
        }
    }
    return false;
    }

    public String getElement(String mypage,String myelement)
    {
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(new FileReader(json));
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }

        JSONArray array = (JSONArray) object.get("pages");
        String value=null;
        String parentName="";
        for (Object o : array) {
            JSONObject page = (JSONObject) o;

            JSONObject pageInfo = (JSONObject) page.get("pageInfo");
            String pagename = (String) pageInfo.get("pageName");
            parentName=(String) pageInfo.get("parent");
            if (pagename.equalsIgnoreCase(mypage))
            {

                JSONArray elements = (JSONArray) page.get("elements");
                for (Object element : elements)
                {
                    JSONObject elem = (JSONObject) element;
                    value= (String) elem.get(myelement);

                    if(value!=null){
                        break;
                    }
                }

                //control parent
                if(value==null)
                {
                    for (Object obj : array)
                    {
                        JSONObject parentPage = (JSONObject) obj;
                         pageInfo = (JSONObject) parentPage.get("pageInfo");
                         pagename = (String) pageInfo.get("pageName");

                        if (pagename.equalsIgnoreCase(parentName))
                        {
                            JSONArray parenEelements = (JSONArray) parentPage.get("elements");
                            for (Object element : parenEelements)
                            {
                                JSONObject elem = (JSONObject) element;
                                value= (String) elem.get(myelement);

                                if(value!=null)
                                {
                                    break;
                                }
                            }
                            if(value!=null)
                            {
                                break;
                            }
                        }
                    }
                }
            }
            if(value!=null)
            {
                break;
            }
        }

        return value;
    }

    public String readConfigInfo(Boolean isLocal)
    {
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(new FileReader(config));
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }
        JSONObject remoteConfig = (JSONObject) object.get("remote");

        return  (String) remoteConfig.get("nodeUrl");
    }
}

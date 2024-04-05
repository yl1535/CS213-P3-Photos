package Photos13;

public class Tags {
    private String TagType;
    private String TagName;
    
    public Tags(String TagType, String TagName){
        this.TagType = TagType;
        this.TagName = TagName;
    }
    
    public String getTagType(){
        return TagType;
    }
    
    public void setTagType(String newType){
        TagType = newType;
    }
    
    public String getTagName(){
        return TagName;
    }
    
    public void setTagName(String newName){
        TagName = newName;
    }
}

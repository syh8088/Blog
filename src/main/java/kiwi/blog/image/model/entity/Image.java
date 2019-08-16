package kiwi.blog.image.model.entity;

import kiwi.blog.common.model.entity.CommonExclusionOfUpdatedAt;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Image extends CommonExclusionOfUpdatedAt {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long imageNo;

  private String name;

  private String type;

  @Lob
  private byte[] data;

  public Image(String name, String type, byte[] data) {
    this.name = name;
    this.type = type;
    this.data = data;
  }
}

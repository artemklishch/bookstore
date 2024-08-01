package org.example.intro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName name;

    public Role(RoleName name) {
        this.name = name;
    }

    @Getter
    public enum RoleName {
        ADMIN("ADMIN"),
        USER("USER");

        private String code;

        private static final Map<String, RoleName> lookup = new HashMap<>();

        static {
            for (RoleName d : RoleName.values()) {
                lookup.put(d.getCode(), d);
            }
        }

        RoleName(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static RoleName get(String code) {
            return lookup.get(code);
        }
    }
}

package com.dimoybiyca.bot.attendancebot.model;

import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users_telegram")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Unique
    @Column(name = "variant", nullable = false)
    @Min(value = 0)
    @Max(value = 27)
    private int variant;

    @NotNull
    @NotBlank
    @Column(name = "name")
    private String name;

    @Unique
    @Column(name = "chat_id", nullable = false)
    long chatId;

    public User() {
    }

    public User(@Unique int variant, String name, @Unique long chatId) {
        this.variant = variant;
        this.name = name;
        this.chatId = chatId;
    }

    public User(long id, @Unique int variant, String name, @Unique long chatId) {
        this.id = id;
        this.variant = variant;
        this.name = name;
        this.chatId = chatId;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public int getVariant() {
        return variant;
    }
    public void setVariant(int variant) {
        this.variant = variant;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public long getChatId() {
        return chatId;
    }
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }


    public int getSubGroup() {
        if(variant <= 14){
            return 1;
        } else {
            return 2;
        }
    }
}

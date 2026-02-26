package com.example.bilibili.service.user.impl;

import com.example.bilibili.dao.UserMapper;
import com.example.bilibili.dao.diy.MyMapper;
import com.example.bilibili.pojo.User;
import com.example.bilibili.pojo.UserExample;
import com.example.bilibili.pojo.diy.VideoGroupInfo;
import com.example.bilibili.service.user.UserService;
import com.example.bilibili.util.MD5Util;
import com.example.bilibili.util.Result;
import com.example.bilibili.util.jwt.JwtUtils;
import com.example.bilibili.util.state.UserState;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Resource
    MyMapper myMapper;

    @Override
    public List<User> selectUserByExample(UserExample userExample) {
        return userMapper.selectByExample(userExample);
    }

    @Override
    public Result LoginDao(User user) {
        String md5password = new MD5Util().digest(user.getPassword());
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andPasswordEqualTo(md5password);
        criteria.andPhoneEqualTo(user.getPhone());
        List<User> userList = userMapper.selectByExample(userExample);
        if(userList.size()==1){
            User user1 = userList.get(0);
            if(user1.getStatus()==UserState.NORMAL){
                user1.setPassword(null);
                String token = JwtUtils.generateToken(String.valueOf(userList.get(0).getId()));
                Map<String,Object> map = new HashMap<>();
                map.put("user",user1);
                map.put("token",token);
                return new Result().successResult("登录成功",map);
            } else if (user1.getStatus()==UserState.FROZEN) {
                return new Result().errorResult("您的账号已被封禁！",null);
            }
        }
        return new Result().errorResult("账号或密码错误",null);
    }

    @Override
    public Result RegistDao(User user) {
        String phone = user.getPhone();
        if(phone!=null){
            //        手机号是否已被注册
//            redius 非持久缓存，高并发

            UserExample userExample = new UserExample();
            userExample.createCriteria().andPhoneEqualTo(phone);
            List<User> userList = userMapper.selectByExample(userExample);
            if(userList.size()>0){
                return new Result().errorResult("该手机号已被注册，请更换手机号重试",null);
            }
            user.setStatus(UserState.NORMAL);
            user.setCreateTime(new Date());
            String md5password = new MD5Util().digest(user.getPassword());
            user.setPassword(md5password);
            int resultCode = userMapper.insert(user);
            if(resultCode>0){
                return new Result().successResult("注册成功",null);
            }
        }
        return new Result().errorResult("系统故障，注册失败！",null);
    }

    @Override
    public User selectUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

//    @Cacheable(value = "users", key = "#id")
//    @Override
//    public User selectUserById(Long id) {
//        //因userjson转User失败，
//        if(redisTemplate.hasKey(String.valueOf(id)) && false){
//            System.out.println("缓存中查询到的");
//            User result = redisTemplate.execute(new RedisCallback<User>() {
//                public User doInRedis(RedisConnection connection) throws DataAccessException {
//                    RedisSerializer<String> serializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
//                    byte[] key = serializer.serialize(String.valueOf(id));
//                    byte[] value = connection.get(key);
//                    if (value == null) {
//                        return null;
//                    }
//                    String user = serializer.deserialize(value);
//                    System.out.println("缓存中查询到的"+user);
//                    return null;
//
//                }
//            });
//            return result;
//        }else{
//            System.out.print("数据库中查询到的");
//            User user = userMapper.selectByPrimaryKey(id);
////            String userJson = ObjectToJson.trans(user);
////            redisTemplate.opsForValue().set(String.valueOf(id),userJson );
//            return user;
//
//        }
//
//    }


    @Override
    public Result updUserById(User user) {
        int r = userMapper.updateByPrimaryKey(user);
        if(r>0){
            Map<String,Object> usermap = new HashMap<>();
            usermap.put("user",user);
            return new Result().successResult("更新成功！",usermap);
        }
        return new Result().errorResult("更新失败！",null);
    }

    @Override
    public Result updPasswordById(Long id, String password) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setPassword(new MD5Util().digest(password));
        int r = userMapper.updateByPrimaryKey(user);
        if(r>0){
            return new Result().successResult("密码已修改",null);
        }else{
            return new Result().errorResult("修改失败",null);
        }
    }



    @Override
    public Long getBeLike(Long authorId) {
        return myMapper.countLikeById(authorId);
    }

    @Override
    public Long getFens(Long authorId) {
        return myMapper.getFens(authorId);
    }

    @Override
    public Long getConcernNumById(Long userId) {
        return myMapper.getConcernNumById(userId);
    }

    @Override
    public Map<String, Object> selectVideoGroupInfoByCategory() {
        List<VideoGroupInfo> top10=myMapper.selectVideoGroupInfoByHot(10);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("top10",top10);
        return resultMap;
    }

    @Override
    public boolean checkVip(Long userId) {
        return userMapper.selectByPrimaryKey(userId) != null;
    }
//
//    @Cacheable(value = "users", key = "#id")
//    public User getUserById(Long id) throws JsonProcessingException {
//        if(redisTemplate.hasKey(String.valueOf(id))){
//            // 根据keyid获取相应的redis值
//            User result = redisTemplate.execute(new RedisCallback<User>() {
//                public User doInRedis(RedisConnection connection) throws DataAccessException {
//                    RedisSerializer<String> serializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
//                    byte[] key = serializer.serialize(String.valueOf(id));
//                    byte[] value = connection.get(key);
//                    if (value == null) {
//                        return null;
//                    }
//                    String name = serializer.deserialize(value);
//                    System.out.println("缓存中的"+name);
//                    return new User();
//                }
//            });
//        }else{
//            System.out.println("数据库中查询的");
//            User user = userMapper.selectByPrimaryKey(id);
//            redisTemplate.opsForValue().set(id+"",user.toString() );
//        }
////        System.out.println("从数据库中获取数据...");
////        String userjson = (String) redisTemplate.opsForValue().get("user_" + id);
////        User user = new User();
////        if (userjson == null) {
////            user = userMapper.selectByPrimaryKey(id);
////            System.out.println(user);
////            redisTemplate.opsForValue().set("user_" + id, new ObjectMapper().writeValueAsString(user));
////            return user;
////        }
////        user = new ObjectMapper().readValue(userjson,user.getClass());
////        return user;
//        return null;
//    }

}

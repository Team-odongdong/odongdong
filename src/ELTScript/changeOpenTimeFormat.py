import json
import pymysql

exp = ['~', '-', '∼']
hot_words = ['~', '-', '∼', "시", "시간"]
dirty_words = [',', '.', '평일', '공휴일', '주말', '휴무', '상시', '정시', '월', '년', '주', '(', ')', ';']


def formatting(str):
    global flag

    temp = str.replace(" ", "")
    if (temp.isalpha()):
        return str

    if (str.isdigit()):
        str_num = int(str)
        if (str_num >= 24):
            flag = False
            return str
        else:
            return str + "시간"

    if (len(str) > 14):
        flag = False
        return str

    for word in dirty_words:
        if (word in str):
            flag = False
            return str

    for word in hot_words:
        if (str.isdigit() == False and word in str):
            break

        if (str.find(word)):
            break

        flag = False
        return str

    str = temp
    for ex in exp:
        if (ex in str and str.count(ex) == 1):
            st, et = str.split(ex)
            # st = st.strip()
            # et = et.strip()
            # if(len(st) != 4 or len(et) != 4 or len(st) != 5 or len(et) != 5):
            #     flag = False
            #     return str

            # if(len(st) == 5 and len(et) == 5):
            #     str = str.replace(ex, "~")
            #     flag = False
            #     return str

            if (":" in st):
                if (":" in et):
                    st_hour, st_min = st.split(":")
                    et_hour, et_min = et.split(":")

                    if (len(st_hour) > 2 or len(st_min) > 2 or len(et_hour) > 2 or len(et_min) > 2):
                        flag = False
                        return str

                    return (st_hour.strip().zfill(2) + ":" + st_min.strip().zfill(2) + "~" + et_hour.strip().zfill(
                        2) + ":" + et_min.strip().zfill(2))
                else:
                    flag = False
                    return str
            elif ("시" in st):
                if ("분" in st or "분" in et):
                    flag = False
                    return str

                st = st.replace("시", "")
                et = et.replace("시", "")
                return st.strip().zfill(2) + ":" + "00" + "~" + et.strip().zfill(2) + ":" + "00"
            else:
                return st.strip().zfill(2) + ":" + "00" + "~" + et.strip().zfill(2) + ":" + "00"

    cnt = 0
    for w in str:
        for ex in exp:
            if (ex == w):
                cnt += 1

    if (cnt == 1):
        flag = True
    else:
        flag = False

    return str


if __name__ == "__main__":
    flag = True
    conn = pymysql.connect(
        host='host', user='user',
        password='password', db='dbName', charset='utf8')
    cur = conn.cursor()
    selectQuery = "SELECT * FROM bathroom LIMIT 2;"
    cur.execute(selectQuery)
    resultSet = cur.fetchall()
    for result in resultSet :
        formatTime = result[10]
        formatTime = formatting(formatTime)
        updateQuery = "UPDATE bathroom SET operation_time = %s WHERE bathroom_id = %s;"
        vals = (formatTime, int(result[0]) )
        cur.execute(updateQuery, vals)
    conn.commit()
    conn.close()

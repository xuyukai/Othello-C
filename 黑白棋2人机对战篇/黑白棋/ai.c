#include<stdio.h>
#include<string.h>
int white_num,black_num;
int flag;
int a0,b0;
int model01[9][9],model02[9][9],model03[9][9],value[9][9]={-576};
int give_pow (int i, int j)
{
    int power[9][9];
    power[1][1]=power[1][8]=power[8][8]=power[8][1]=10;
    power[2][1]=power[1][2]=power[2][2]=power[7][1]=power[7][2]=power[8][2]=power[8][7]=power[7][7]=power[7][8]=power[1][7]=power[2][7]=power[2][8]=-9;
    power[3][1]=power[6][1]=power[3][3]=power[3][6]=power[1][6]=power[3][8]=power[8][3]=power[8][6]=power[6][8]=power[6][3]=power[6][6]=power[1][3]=8;
    power[4][5]=power[5][4]=power[4][4]=power[5][5]=1;
    power[4][3]=power[5][3]=power[3][4]=power[3][5]=power[4][6]=power[5][6]=power[6][4]=power[6][5]=2;
    power[3][2]=power[2][3]=power[2][6]=power[3][7]=power[6][2]=power[7][3]=power[7][7]=power[6][7]=-4;
    power[4][2]=power[5][2]=power[2][4]=power[2][5]=power[4][6]=power[5][6]=power[7][4]=power[7][5]=-3;
    power[1][4]=power[1][5]=power[4][8]=power[5][8]=power[4][1]=power[5][1]=power[8][4]=power[8][5]=4;
    return power[i][j];
}
void chessboard(int cb[9][9])
{
    int i,j;
    for(i=0;i<9;i++)
    {
        cb[i][0]=i;
    }
    for(j=0;j<9;j++)
    {
        cb[0][j]=j;
    }
    cb[4][4]=1;
    cb[5][5]=1;
    cb[4][5]=2;
    cb[5][4]=2;
}

void flip(int cb[9][9],int turn,int x,int y)
{
    int i=x, j=y,t,k;
    if(cb[x+1][y]==2-turn)
    {
        for(i=x+1;i<=8;i++)
        {
            if(cb[i][j]==2-turn)
                continue;
            if(cb[i][j]==0)
                break;
            if(cb[i][j]==turn+1)
            {
                t=i;
                k=j;
                for(i=x+1;i<t;i++)
                {
                    cb[i][j]=turn+1;
                }
                break;
            }
        }
    }
    i=x;
    j=y;
    if(cb[x-1][y]==2-turn)
    {
        for(i=x-1;i>=1;i--)
        {
            if(cb[i][j]==2-turn)
                continue;
            if(cb[i][j]==0)
                break;
            if(cb[i][j]==turn+1)
            {
                t=i;
                k=j;
                for(i=x-1;i>t;i--)
                {
                    cb[i][j]=turn+1;
                }
                break;
            }
        }
    }
    i=x;
    j=y;
    if(cb[x][y+1]==2-turn)
    {
        for(j=y+1;j<=8;j++)
        {
            if(cb[i][j]==2-turn)
                continue;
            if(cb[i][j]==0)
                break;
            if(cb[i][j]==turn+1)
            {
                t=i;
                k=j;
                for(j=y+1;j<k;j++)
                {
                    cb[i][j]=turn+1;
                }
                break;
            }
        }
    }
    i=x;
    j=y;
    if(cb[x][y-1]==2-turn)
    {
        for(j=y-1;j>=1;j--)
        {
            if(cb[i][j]==2-turn)
                continue;
            if(cb[i][j]==0)
                break;
            if(cb[i][j]==turn+1)
            {
                t=i;
                k=j;
                for(j=y-1;j>k;j--)
                {
                    cb[i][j]=turn+1;
                }
                break;
            }
        }
    }
    i=x;
    j=y;
    if(cb[x+1][y+1]==2-turn)
    {
        for(i=x+1,j=y+1;i<=8&&j<=8;i++,j++)
        {
            if(cb[i][j]==2-turn)
                continue;
            if(cb[i][j]==0)
                break;
            if(cb[i][j]==turn+1)
            {
                t=i;
                k=j;
                for(i=x+1,j=y+1;i<t&&j<k;i++,j++)
                {
                    cb[i][j]=turn+1;
                }
                break;
            }
        }
    }
    i=x;
    j=y;
    if(cb[x-1][y-1]==2-turn)
    {
        for(i=x-1,j=y-1;i>=1&&j>=1;i--,j--)
        {
            if(cb[i][j]==2-turn)
                continue;
            if(cb[i][j]==0)
                break;
            if(cb[i][j]==turn+1)
            {
                t=i;
                k=j;
                for(i=x-1,j=y-1;i>t&&j>k;i--,j--)
                {
                    cb[i][j]=turn+1;
                }
                break;
            }
        }
    }
    i=x;
    j=y;
    if(cb[x+1][y-1]==2-turn)
    {
        for(i=x+1,j=y-1;i<=8&&j>=1;i++,j--)
        {
            if(cb[i][j]==2-turn)
                continue;
            if(cb[i][j]==0)
                break;
            if(cb[i][j]==turn+1)
            {
                t=i;
                k=j;
                for(i=x+1,j=y-1;i<t&&j>k;i++,j--)
                {
                    cb[i][j]=turn+1;
                }
                break;
            }
        }
    }
    i=x;
    j=y;
    if(cb[x-1][y+1]==2-turn)
    {
        for(i=x-1,j=y+1;i>=1&&j<=8;i--,j++)
        {
            if(cb[i][j]==2-turn)
                continue;
            if(cb[i][j]==0)
                break;
            if(cb[i][j]==turn+1)
            {
                t=i;
                k=j;
                for(i=x-1,j=y+1;i>t&&j<k;i--,j++)
                {
                    cb[i][j]=turn+1;
                }
                break;
            }
        }
    }
}

void check(int cb[9][9],int turn)
{
    int t,k,i,j;
    {
        for(i=1;i<9;i++)
        {
            for(j=1;j<9;j++)
            {
                if(cb[i][j]==turn+1)
                {
                    t=i;
                    k=j;
                    if(cb[t+1][k]==2-turn&&t<8)
                    {
                        for(t=i+1;t<=8;t++)
                        {
                            if(cb[t][k]==2-turn)
                            continue;
                            if(cb[t][k]==3||cb[t][k]==turn+1)
                            break;
                            if(cb[t][k]==0)
                            {
                                cb[t][k]=3;
                                flag=1;
                                break;
                            }
                        }
                    }
                    t=i;
                    k=j;
                    if(cb[t-1][k]==2-turn&&t>1)
                    {
                        for(t=i-1;t>=1;t--)
                        {
                            if(cb[t][k]==2-turn) continue;
                            if(cb[t][k]==3||cb[t][k]==turn+1) break;
                            if(cb[t][k]==0)
                            {
                                cb[t][k]=3;
                                flag=1;
                                break;
                            }
                        }
                    }
                    t=i;
                    k=j;
                    if(cb[t][k-1]==2-turn&&k>1)
                    {

                        for(k=j-1;k>=1;k--)
                        {
                            if(cb[t][k]==2-turn) continue;
                            if(cb[t][k]==3||cb[t][k]==turn+1) break;
                            if(cb[t][k]==0)
                            {
                                cb[t][k]=3;
                                flag=1;
                                break;
                            }
                        }
                    }
                    t=i;
                    k=j;
                    if(cb[t][k+1]==2-turn&&k<8)
                    {
                        for(k=j+1;k<=8;k++)
                        {
                            if(cb[t][k]==2-turn) continue;
                            if(cb[t][k]==3||cb[t][k]==turn+1) break;
                            if(cb[t][k]==0)
                            {
                                cb[t][k]=3;
                                flag=1;
                                break;
                            }
                        }
                    }
                    t=i;
                    k=j;
                    if(cb[t-1][k-1]==2-turn&&t>1&&k>1)
                    {
                        for(t=i-1,k=j-1;t>=1&&k>=1;t--,k--)
                        {
                            if(cb[t][k]==2-turn) continue;
                            if(cb[t][k]==3||cb[t][k]==turn+1) break;
                            if(cb[t][k]==0)
                            {
                                cb[t][k]=3;
                                flag=1;
                                break;
                            }
                        }
                    }
                    t=i;
                    k=j;
                    if(cb[t+1][k+1]==2-turn&&t<8&&k<8)
                    {
                        for(t=i+1,k=j+1;t<=8&&k<=8;t++,k++)
                        {
                            if(cb[t][k]==2-turn) continue;
                            if(cb[t][k]==3||cb[t][k]==turn+1) break;
                            if(cb[t][k]==0)
                            {
                                cb[t][k]=3;
                                flag=1;
                                break;
                            }
                        }
                    }
                    t=i;
                    k=j;
                    if(cb[t-1][k+1]==2-turn&&t>1&&k<8)
                    {
                        for(t=i-1,k=j+1;t>=1&&k<=8;t--,k++)
                        {
                            if(cb[t][k]==2-turn) continue;
                            if(cb[t][k]==3||cb[t][k]==turn+1) break;
                            if(cb[t][k]==0)
                            {
                                cb[t][k]=3;
                                flag=1;
                                break;
                            }
                        }
                    }
                    t=i;
                    k=j;
                    if(cb[t+1][k-1]==2-turn&&t<8&&k>1)
                    {
                        for(t=i+1,k=j-1;t<=8&&k>=1;t++,k--)
                        {
                            if(cb[t][k]==2-turn) continue;
                            if(cb[t][k]==3||cb[t][k]==1+turn) break;
                            if(cb[t][k]==0)
                            {
                                cb[t][k]=3;
                                flag=1;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}

int evaluate (int model[9][9],int color)
{
    int i,j,score=0;
    for(i=1;i<9;i++)
    {
        for(j=1;j<9;j++)
        {
            if(model[i][j]==color+1)
            {
                score+=give_pow(i,j);
            }
        }
    }
    return score;
}
void predict (int cb[9][9], int color)
{
    int i,j,t,k,n,m,a,b,b1,a1=640,b2,a2=-576;
    check(cb,color);
    for(i=1;i<9;i++)
    {
        for(j=1;j<9;j++)
        {
            if(cb[i][j]==3)
            {
                memcpy(model01,cb,sizeof(cb));
                model01[i][j]=color+1;
                flip(model01,color,i,j);
                flag=0;
                check(model01,~color);
                if(flag==0)
                {
                   for(a=1;a<9;a++)
                   {
                        for(b=1;b<9;b++)
                        {
                            if(model01[a][b]==color+1)
                            {
                                value[i][j]+=give_pow(a,b);
                            }
                        }
                   }
                }
                else
                {
                    for(t=1;t<9;t++)
                    {
                        for(k=1;k<9;k++)
                        {
                            if(model01[t][k]==3)
                            {
                                memcpy(model02,model01,sizeof(model01));
                                model02[t][k]=(~color)+1;
                                flip(model02,~color,t,k);
                                flag=0;
                                check(model02,color);
                                if(flag==0)
                                {
                                    for(a=1;a<9;a++)
                                    {
                                        for(b=1;b<9;b++)
                                        {
                                            if(model02[a][b]==color+1)
                                            {
                                                b1+=give_pow(a,b);
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    for(m=1;m<9;m++)
                                    {
                                        for(n=1;n<9;n++)
                                        {
                                            if(model02[m][n]==3)
                                            {
                                                 memcpy(model03,model02,sizeof(model02));
                                                 model03[m][n]=color+1;
                                                 flip(model03, color, m ,n);
                                                 for(a=1;a<9;a++)
                                                 {
                                                    for(b=1;b<9;b++)
                                                    {
                                                        if(model03[a][b]==color+1)
                                                        {
                                                            b2+=give_pow(a,b);
                                                        }
                                                    }
                                                }
                                                if(b2>=a2) a2=b2;
                                            }
                                        }
                                    }
                                    b1=a2;
                                    if(b1<=a1) a1=b1;
                                }
                            }
                        }
                    }
                    value[i][j]=a1;
                }
            }
        }
    }
    int min=-576;
    for(i=1;i<9;i++)
    {
        for(j=1;j<9;j++)
        {
            if(value[i][j]>=min&&cb[i][j]==3){
                a0=i;b0=j;min=value[i][j];
            }
        }
    }
    cb[a0][b0]=color+1;
}
void print(int cb[9][9])
{
    printf("\n\n");
    int i,j;
    for(i=0;i<9;i++)
    {
        for(j=0;j<9;j++)
        {
            if(i==0)
            {
                if(j==0)
                {
                    printf("    ");
                }
                printf("%d ",cb[i][j]);
            }
            else if(j==0)
            {
                printf("    %d ",cb[i][j]);
            }
            else if(cb[i][j]==0) printf("·");
            else if(cb[i][j]==1) printf("0 ");
            else if(cb[i][j]==2) printf("@ ");
            else if(cb[i][j]==3) printf("- ");//“. ”是正常格子，值为0；“0”是白棋，值为1；“@”是黑棋；值为2；“*”是可用位置，值为3.
        }
        printf("\n");
    }
 }
 void count(int cb[9][9])
{
    white_num=0;
    black_num=0;
    int i,j;
    for(i=1;i<=8;i++)
    {
        for(j=1;j<=8;j++)
        {
            if(cb[i][j]==1) white_num++;
            if(cb[i][j]==2) black_num++;
        }
    }
}

int two(int cb[9][9])
{
            system("cls");
            int turn=1;
            int x,y,i,j;
            chessboard(cb);
            turn%=2;
            check(cb,turn);
            print(cb);
            printf("\n");
            count(cb);
            printf("    WHITE: %d points\n",white_num);
            printf("    BLACK: %d points\n",black_num);
            printf("    黑方落子，请输入:棋子的行数[空格]列数\n    ");
            white_num=0;
            black_num=0;
            while(1)
            {
                scanf("%d%d",&x,&y);
                if(x<1||x>8||y<1||y>8) printf("     智障吧你！再输一次好嘛？\n    ");
                else if(cb[x][y]!=3) printf("    智障吧你！再输一次好嘛？\n    ");
                else
                {
                    system("cls");
                    cb[x][y]=turn+1;
                    for(i=1;i<=8;i++)
                    {
                        for(j=1;j<=8;j++)
                        {
                            if(cb[i][j]==3) cb[i][j]=0;
                        }
                    }
                    flip(cb,turn,x,y);
                    turn++;
                    turn%=2;
                    check(cb,turn);
                    count(cb);
                    if(flag==1||(white_num+black_num==64)||white_num==0||black_num==0)
                    {
                        print(cb);
                        printf("\n");
                        printf("    WHITE: %d points\n",white_num);
                        printf("    BLACK: %d points\n",black_num);
                        if(white_num+black_num==64)
                        {
                            if(white_num>black_num)
                        {
                            printf("    白方获胜！WHITE %d : BLACK %d\n",white_num,black_num);
                        }
                        if(white_num<black_num)
                        {
                            printf("    黑方获胜！WHITE %d : BLACK %d\n",white_num,black_num);
                        }
                        if(white_num==black_num)
                        {
                            printf("    平局啦！\n");
                        }
                        break;
                        }
                        if(white_num==0)
                        {
                            printf("    黑方获胜！WHITE %d : BLACK %d\n",white_num,black_num);
                            break;
                        }
                        if(black_num==0)
                        {
                            printf("    白方获胜！WHITE %d : BLACK %d\n",white_num,black_num);
                            break;
                        }
                        if(turn==0)
                            printf("\n    黑方落子于(%d,%d)\n    白方落子，请输入:棋子的行数[空格]列数\n    ",x,y);
                        else
                            printf("\n    白方落子于(%d,%d)\n    黑方落子，请输入:棋子的行数[空格]列数\n    ",x,y);
                    }
                    else
                    {
                        if(turn==0)
                        {
                            check(cb,(turn+1)%2);
                            print(cb);
                            printf("\n");
                            printf("    白方无法落子，黑方继续\n    ");
                        }
                        else
                        {
                            check(cb,(turn+1)%2);
                            print(cb);
                            printf("\n");
                            printf("    黑方无法落子，白方继续\n    ");
                        }
                        turn++;
                        turn%=2;
                    }
                    flag=0;
                }
            }
            printf("    游戏结束，是否再来一局？\n  Yes: 1    No: 2\n    ");
            int again;
            scanf("%d",&again);
            return again;
}
int main()
{
    int cb[9][9]={0},x,y,i,j;
    int know;
    printf("欢迎来到游戏 黑白棋！\n");
    printf("您是否了解此游戏的规则？\nYes: 1;     No: 2;\n");
    scanf("%d",&know);
    if(know==2)
    {
        printf("“. ”代表没有落子\n“0”是白棋\n“@”是黑棋\n“-”是可用位置\n");
        printf("请注意，落子时输入样例：x y\n");
        system("pause");
    }
    system("cls");
    while(1)
    {
        printf("请选择单人或双人模式\n单人请输入\"1\";双人请输出\"2\"\n");
        int mod;
        scanf("%d",&mod);
        if(mod==2)//shuang ren mo shi
        {
            if(two(cb)==2)
            {
                break;
            }
            else system("cls");
        }
        else//ren ji dui zhan
        {
                    system("cls");
                    chessboard(cb);
                    check(cb,1);
                    print(cb);
                    printf("\n");
                    count(cb);
                    printf("    WHITE: %d points\n",white_num);
                    printf("    BLACK: %d points\n",black_num);
                    printf("    黑方落子，请输入:棋子的行数[空格]列数\n    ");
                    white_num=0;
                    black_num=0;
                    while(1)
                    {
                        scanf("%d%d",&x,&y);
                        if(x<1||x>8||y<1||y>8) printf("     智障吧你！再输一次好嘛？\n    ");
                        else if(cb[x][y]!=3) printf("    智障吧你！再输一次好嘛？\n    ");
                        else
                        { system("cls");
                            cb[x][y]=2;
                            for(i=1;i<=8;i++)
                            {
                                for(j=1;j<=8;j++)
                                {
                                    if(cb[i][j]==3) cb[i][j]=0;
                                }
                            }
                            flip(cb,1,x,y);
                            flag=0;
                            check(cb,0);
                            count(cb);
                            if(flag==1||(white_num+black_num==64))
                            {
                               if(white_num+black_num==64)
                               {
                                    if(white_num<black_num)
                                    {
                                        printf("    黑方获胜！WHITE %d : BLACK %d\n",white_num,black_num);
                                        break;
                                    }
                                    if(white_num==black_num)
                                    {
                                        printf("    平局啦！\n");
                                        break;
                                    }
                               }
                               if(white_num==0)
                                    printf("    黑方获胜！WHITE %d : BLACK %d\n",white_num,black_num);
                               else
                               {
                                    for(i=1;i<=8;i++)
                                    {
                                        for(j=1;j<=8;j++)
                                        {
                                            if(cb[i][j]==3) cb[i][j]=0;
                                        }
                                    }
                                    predict(cb,0);
                                    flip(cb,0,a0,b0);
                                    for(i=1;i<=8;i++)
                                    {
                                        for(j=1;j<=8;j++)
                                        {
                                            if(cb[i][j]==3) cb[i][j]=0;
                                        }
                                    }
                                    check(cb,1);

                                    print(cb);
                                    printf("\n");
                                    white_num=0;
                                    black_num=0;
                                    count(cb);
                                    if(white_num+black_num==64||black_num==0)
                                    {
                                       if(white_num>black_num)
                                        {
                                            printf("    白方获胜！WHITE %d : BLACK %d\n",white_num,black_num);
                                            break;
                                        }
                                        if(white_num==black_num)
                                        {
                                            printf("    平局啦！\n");
                                            break;
                                        }
                                    }
                                    printf("    WHITE: %d points\n",white_num);
                                    printf("    BLACK: %d points\n",black_num);
                                    printf("\n    白方落子于(%d,%d)\n    黑方落子，请输入:棋子的行数[空格]列数\n    ",a0,b0);
                               }
                            }
                            else
                            {
                                check(cb,1);
                                print(cb);
                                printf("\n");
                                printf("    白方无法落子，黑方继续\n    ");
                            }
                        }
                    }
                    printf("    游戏结束，是否再来一局？\n  Yes: 1    No: 2\n    ");
                    int again;
                    scanf("%d",&again);
                    if(again==2) break;


                }
        }
    }



#include<stdio.h>
#include<string.h>
int flag=0;
int white_num,black_num;
void chessboard(int cb[9][9])
{
    int i,j;
/*    for(i=1;i<=8;i++)
    {
     for(j=1;j<=8;j++)
        {
            cb[i][j]=0;
        }
    }*/
    for(i=0;i<9;i++)
    {
        cb[i][0]=i;
    }
    for(j=0;j<9;j++)
    {
        cb[0][j]=j;
    }
    //cb[1][1]=3;
    cb[4][4]=1;
    cb[5][5]=1;
    cb[4][5]=2;
    cb[5][4]=2;
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
void check(int cb[9][9],int turn){
    int t,k,i,j;
    {
        for(i=1;i<9;i++)
        {
            for(j=1;j<9;j++)
            {
                if(cb[i][j]==turn+1)
                {
                   // printf("i=%d j=%d\n",i,j);
                    t=i;
                    k=j;
                    if(cb[t+1][k]==2-turn&&t<8)
                    {
                       // printf("here1!");
                        for(t=i+1;t<=8;t++)
                        {
                       //     printf("here2!");
                            if(cb[t][k]==2-turn)
                            continue;
                            if(cb[t][k]==3||cb[t][k]==turn+1)
                            break;
                            if(cb[t][k]==0)
                            {
                                cb[t][k]=3;
                                flag=1;
                               // printf("here3!");
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
                        //printf("t=%d  k=%d\n",t,k);
                       // printf("%d\n",turn);
                        for(t=i+1,k=j+1;t<=8&&k<=8;t++,k++)
                        {
                           // printf("%d\n",cb[t][k]);
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
int main()
{
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
        int turn=1;
        int cb[9][9]={0},x,y,i,j;
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
                //printf("\n%d\n",cb[6][6]);
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
        if(again==2)
        {
            break;
        }
        else system("cls");
    }
}

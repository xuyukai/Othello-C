#include <stdio.h>
#include <math.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include "aiplayer-1155107694.h"
//#include "aiplayer-1155107694.c"
int valid_pos_1155107694[100] ;
void initialize(int*gameboard_1155107694 )
{
    int i , j ;
    for( i = 0 ; i <= 9 ; i++ )
        for( j = 0 ; j <= 9 ; j++ )
            gameboard_1155107694[i*10+j] = 3 ;
    for( i = 1 ; i <= 8 ; i++ )
        for( j = 1 ; j <= 8 ; j++ )
            gameboard_1155107694[i*10+j] = 0 ;
    gameboard_1155107694[45]=gameboard_1155107694[54]= 1 ;
    gameboard_1155107694[44]=gameboard_1155107694[55]= 2 ;
}

void print(int*gameboard_1155107694 )
{
    int i , j , black = 0 , white = 0;
    printf("%c",' ');
    for( i = 1 ; i <= 8 ; i ++  )
    {
        printf(" %d ", i );
        if( i == 8 )   printf("\n");
    }
    for( i = 1 ; i <= 8 ; i++ )
    {
        for( j = 0 ; j <= 8 ; j++ )
        {
            if( j == 0)
            {
                printf("%d", i );
                continue;
            }
            if( gameboard_1155107694[i*10+j] == 0 )
                printf(" %c ",'.');
            if( gameboard_1155107694[i*10+j] == 1 )
                printf(" %c ",'#');
            if( gameboard_1155107694[i*10+j] == 2 )
                printf(" %c ",'O');
            if( j == 8 )
                printf("\n");
        }
    }
     for( i = 1 ; i <= 8 ; i++ )
        for( j = 1 ; j <= 8 ; j++ )
        {
             if(gameboard_1155107694[i*10+j] == 1)
                    black++;
             if(gameboard_1155107694[i*10+j] == 2)
                    white++;
        }
     printf("[ # = %d O = %d ]\n",black,white);
}

int find_valid_black( int*gameboard_1155107694,int valid_pos_1155107694[100] )
{
    int  a , count = 0 ;
    for( a = 0 ; a < 100 ; a++ )
        valid_pos_1155107694[a] = 0 ;

    int black_pos[100] ;
    int i , j , z = 0 , h ;
    for( i = 1 ;i <= 8 ; i++ )
        for( j = 1 ; j <= 8 ; j++ )
            if(gameboard_1155107694[i*10+j]==1)
                black_pos[z++]=i*10+j;
    for( h = 0 ; h <= z-1 ; h++ )
    {
        int row , col ;
        row = black_pos[h]/10;
        col = black_pos[h]%10;
        for( i = -1 ; i <= 1 ; i++ )
        {
            for( j = -1 ; j <= 1 ; j++ )
            {
                if( i == 0 && j == 0 )
                   continue ;
                if( gameboard_1155107694[(row+i)*10+(col+j)] == 2 )
                {
                    for( a = 1 ; row+i*a >= 1 && row+i*a <= 8 && col+j*a >= 1 && col+j*a <= 8 ; a++ )
                    {
                        if( gameboard_1155107694[(row+i*a)*10+(col+j*a)] == 1 )
                            break;
                        if( gameboard_1155107694[(row+i*a)*10+(col+j*a)] == 2 )
                            continue;
                        if( gameboard_1155107694[(row+i*a)*10+(col+j*a)] == 0 )
                        {
                            valid_pos_1155107694[count++]=(row+i*a)*10+(col+j*a);
                            break;
                        }
                    }
                }
            }
        }
    }
    return count ;
}

int find_valid_white( int*gameboard_1155107694 , int valid_pos_1155107694[100] )
{
    int  a ;
    for( a = 0 ; a < 100 ; a++ )
        valid_pos_1155107694[a] = 0 ;
    int white_pos[100] ;  int i , j , z = 0 , count = 0 ,  h ;
    for(i = 1; i <= 8 ; i++ )
        for( j = 1 ; j <=8 ; j++ )
            if(gameboard_1155107694[i*10+j] == 2 )
                white_pos[z++]= i*10+j ;
    for( h = 0 ; h <= z - 1 ; h++ )
    {
        int row , col ;
        row = white_pos[h]/10;
        col = white_pos[h]%10;
        for( i = -1 ; i <= 1 ; i++ )
        {
            for( j = -1 ; j <= 1 ; j++ )
            {
                if( i == 0 && j == 0 )
                    continue ;
                if(gameboard_1155107694[(row+i)*10+(col+j)] == 1 )
                {
                    for( a = 1 ; row+i*a >= 1 && row+i*a <= 8 && col+j*a >= 1 && col+j*a <= 8 ; a++ )
                    {
                        if(gameboard_1155107694[(row+i*a)*10+(col+j*a)] == 2 )
                            break;
                        if(gameboard_1155107694[(row+i*a)*10+(col+j*a)] == 1 )
                            continue;
                        if(gameboard_1155107694[(row+i*a)*10+(col+j*a)] == 0 )
                        {
                            valid_pos_1155107694[count++]=(row+i*a)*10+(col+j*a);
                            break;
                        }
                    }
                }
            }
        }
    }
    return count ;
}

void flip_black( int*gameboard_1155107694 , int pos )
{
    int row , col , condition = 0 , i , j , a  , b ;
    row = pos/10 ;
    col = pos%10 ;
    for( i = -1 ; i <= 1 ; i++ )
    {
        for( j = -1 ; j <= 1 ; j++ )
        {
            if( i == 0 && j == 0 )
                continue ;
            if(gameboard_1155107694[(row+i)*10+(col+j)] == 2 )
            {
                for( a = 1 ; row+i*a >= 1 && row+i*a <= 8 && col+j*a >= 1 && col+j*a <= 8 ; a++ )
                {
                    if(gameboard_1155107694[ (row+i*a)*10 + (col+j*a) ] == 2 )
                        continue ;
                    if(gameboard_1155107694[ (row+i*a)*10 + (col+j*a) ] == 0 )
                        break ;
                    if(gameboard_1155107694[ (row+i*a)*10 + (col+j*a) ] == 1 )
                    {
                        condition = 1 ;
                        break ;
                    }
                }
            }
            if( condition == 1 )
            {
                for( b = 1 ; b <= a ; b++ )
                    gameboard_1155107694[ (row+i*b)*10 + (col+j*b) ] = 1 ;
            }
            condition = 0;
        }
    }
}

void flip_white( int*gameboard_1155107694 , int pos )
{
    int row , col , condition = 0 , i , j , a  , b ;
    row = pos/10 ;
    col = pos%10 ;
    for( i = -1 ; i <= 1 ; i++ )
    {
        for( j = -1 ; j <= 1 ; j++ )
        {
            if( i == 0 && j == 0 )
                continue ;
            if(gameboard_1155107694[(row+i)*10+(col+j)] == 1 )
            {
                for( a = 1 ; row+i*a >= 1 && row+i*a <= 8 && col+j*a >= 1 && col+j*a <= 8 ; a++ )
                {
                    if(gameboard_1155107694[ (row+i*a)*10 + (col+j*a) ] == 1 )
                        continue ;
                    if(gameboard_1155107694[ (row+i*a)*10 + (col+j*a) ] == 0 )
                        break;
                    if(gameboard_1155107694[ (row+i*a)*10 + (col+j*a) ] == 2 )
                    {
                        condition = 1 ;
                        break ;
                    }
                }
            }
            if( condition == 1 )
            {
                for( b = 1 ; b <= a ; b++ )
                    gameboard_1155107694[ (row+i*b)*10 + (col+j*b) ] = 2 ;
            }
            condition = 0;
        }
    }
}

void print_result(int*gameboard_1155107694)
{
        int  i , j , black = 0 , white = 0 ;
        for( i = 1 ; i <= 8 ; i++ )
            for( j = 1 ; j <= 8 ; j++ )
            {
                if( gameboard_1155107694[i*10+j] == 1 )
                    black++;
                if( gameboard_1155107694[i*10+j] == 2 )
                    white++;
            }
         printf("Black = %d    White = %d" ,black,white);
         if( black >  white )   printf("Black wins");
    else if( black == white )   printf("Draw game" );
    else if( black <  white )   printf("White wins");
}

void human_player(int*gameboard_1155107694)
{
    int i , pos , count ;
    initialize(gameboard_1155107694);
    print(gameboard_1155107694);
    while ( 1 )
    {
        while( 1 )
        {
            printf("Black turn(#):\n");
            count = find_valid_black(gameboard_1155107694,valid_pos_1155107694);
            if(     count    ==     0      )
            {
                printf("You need to pass:(\n");
                break;
            }
            else
            {
                scanf("%d",&pos);
                for( i = 0 ; i <= count - 1 ; i++ )
                {
                    if( pos == valid_pos_1155107694[ i ] )
                    {
                        gameboard_1155107694[pos] = 1 ;
                        flip_black( gameboard_1155107694 , pos );
                        print(gameboard_1155107694);
                        break;
                    }
                }
                if( i == count && pos != valid_pos_1155107694[count-1] )
                {
                    printf("Invalid position:(\n");
                    continue;
                }
                else  break;
            }
        }
        while( 1 )
        {
            printf("White turn(O):\n");
            count = find_valid_white(gameboard_1155107694,valid_pos_1155107694);
            if( count == 0 )
            {
                printf("You need to pass:(\n");
                break;
            }
            else
            {
                scanf("%d",&pos);
                for(i = 0 ; i <= count - 1 ; i++ )
                {
                    if( pos == valid_pos_1155107694[i] )
                    {
                        gameboard_1155107694[pos] = 2 ;
                        flip_white( gameboard_1155107694 , pos );
                        print(gameboard_1155107694);
                        break;
                    }
                }
                if( i == count && pos != valid_pos_1155107694[ count - 1 ] )
                {
                    printf("Invalid position:(\n");
                    continue;
                }
                else break ;
            }
        }
        if(find_valid_white(gameboard_1155107694,valid_pos_1155107694) == 0 &&
           find_valid_black(gameboard_1155107694,valid_pos_1155107694) == 0 )
        {
            print_result(gameboard_1155107694);
            break;
        }
    }
}

int main()
{
    int *gameboard_1155107694 =NULL ;
    gameboard_1155107694 = (int*)malloc(sizeof(int)*100);

    int  mode  , player ;//BLK FOR 1   WHT FOR 2
    printf("Please select the battle mode:)\n");
    printf("1 - human vs human    2 - computer player\n");
    scanf("%d",&mode);
    if( mode == 1 )
       human_player(gameboard_1155107694);
    if( mode == 2 )
    {

        printf("Select the color \n");
        printf("1 - black 2 - white \n");
        scanf("%d",&player);

        int i , pos , count ;
        initialize(gameboard_1155107694);
        print(gameboard_1155107694);
        if( player == 2 )
        {
        while ( 1 )
        {
            while( 1 )
            {
                printf("Black turn(#):\n");
                count = find_valid_black(gameboard_1155107694,valid_pos_1155107694);
                if(     count    ==     0      )
                {
                    printf("You need to pass:(\n");
                    break;
                }
                else
                {
                    pos = ai_player_1155107694( player , gameboard_1155107694 );
                    for( i = 0 ; i <= count - 1 ; i++ )
                    {
                        if( pos == valid_pos_1155107694[ i ] )
                        {
                            gameboard_1155107694[pos] = 1 ;
                            flip_black( gameboard_1155107694 , pos );
                            print(gameboard_1155107694);
                            break;
                        }
                    }
                    if( i == count && pos != valid_pos_1155107694[count-1] )
                    {
                        printf("Invalid position:(\n");
                        continue;
                    }
                    else  break;
                }
            }
            while( 1 )
            {
                printf("White turn(O):\n");
                count = find_valid_white(gameboard_1155107694,valid_pos_1155107694);
                if( count == 0 )
                {
                    printf("You need to pass:(\n");
                    break;
                }
                else
            {
                scanf("%d",&pos);
                for(i = 0 ; i <= count - 1 ; i++ )
                {
                    if( pos == valid_pos_1155107694[i] )
                    {
                        gameboard_1155107694[pos] = 2 ;
                        flip_white( gameboard_1155107694 , pos );
                        print(gameboard_1155107694);
                        break;
                    }
                }
                if( i == count && pos != valid_pos_1155107694[ count - 1 ] )
                {
                    printf("Invalid position:(\n");
                    continue;
                }
                else break ;
            }
        }
        if(find_valid_white(gameboard_1155107694,valid_pos_1155107694) == 0 &&
           find_valid_black(gameboard_1155107694,valid_pos_1155107694) == 0 )
        {
            print_result(gameboard_1155107694);
            break;
        }
    }
        }


        if( player == 1 )
        {
        while ( 1 )
        {
            while( 1 )
            {
                printf("Black turn(#):\n");
                count = find_valid_black(gameboard_1155107694,valid_pos_1155107694);
                if(     count    ==     0      )
                {
                    printf("You need to pass:(\n");
                    break;
                }
                else
                {
                    scanf("%d",&pos);
                    for( i = 0 ; i <= count - 1 ; i++ )
                    {
                        if( pos == valid_pos_1155107694[ i ] )
                        {
                            gameboard_1155107694[pos] = 1 ;
                            flip_black( gameboard_1155107694 , pos );
                            print(gameboard_1155107694);
                            break;
                        }
                    }
                    if( i == count && pos != valid_pos_1155107694[count-1] )
                    {
                        printf("Invalid position:(\n");
                        continue;
                    }
                    else  break;
                }
            }
            while( 1 )
            {
                printf("White turn(O):\n");
                count = find_valid_white(gameboard_1155107694,valid_pos_1155107694);
                if( count == 0 )
                {
                    printf("You need to pass:(\n");
                    break;
                }
                else
            {
                pos = ai_player_1155107694( player , gameboard_1155107694 );
                for(i = 0 ; i <= count - 1 ; i++ )
                {
                    if( pos == valid_pos_1155107694[i] )
                    {
                        gameboard_1155107694[pos] = 2 ;
                        flip_white( gameboard_1155107694 , pos );
                        print(gameboard_1155107694);
                        break;
                    }
                }
                if( i == count && pos != valid_pos_1155107694[ count - 1 ] )
                {
                    printf("Invalid position:(\n");
                    continue;
                }
                else break ;
            }
        }
        if(find_valid_white(gameboard_1155107694,valid_pos_1155107694) == 0 &&
           find_valid_black(gameboard_1155107694,valid_pos_1155107694) == 0 )
        {
            print_result(gameboard_1155107694);
            break;
        }
    }
        }
    }
    free(gameboard_1155107694);
    gameboard_1155107694 = NULL ;
    return 0 ;
}
